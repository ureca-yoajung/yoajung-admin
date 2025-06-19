package com.ureca.yoajungadmin.chatbot.batch;

import com.ureca.yoajungadmin.chatbot.entity.ChatHistory;
import com.ureca.yoajungadmin.chatbot.entity.ChatStatistic;
import com.ureca.yoajungadmin.chatbot.entity.ChatType;
import com.ureca.yoajungadmin.chatbot.repository.ChatHistoryRepository;
import com.ureca.yoajungadmin.chatbot.repository.ChatStatisticRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import static com.ureca.yoajungadmin.chatbot.entity.ChatStatType.WEEKLY_CHAT_ACTIVE_USER_COUNT;
import static com.ureca.yoajungadmin.chatbot.entity.ChatStatType.WEEKLY_CHAT_MESSAGE_COUNT;

@Configuration
@RequiredArgsConstructor
public class ChatWeeklyBatch {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final ChatHistoryRepository chatHistoryRepository;
    private final ChatStatisticRepository chatStatisticRepository;

    @Bean
    public Job weeklyMessageJob() {
        return new JobBuilder("weeklyMessageJob", jobRepository)
                .start(weeklyMessageStep())
                .build();
    }

    @Bean
    public Step weeklyMessageStep() {
        return new StepBuilder("weeklyMessageStep", jobRepository)
                .tasklet(weeklyMessageCountTasklet(), platformTransactionManager)
                .build();
    }

    private Tasklet weeklyMessageCountTasklet() {
        return (contribution, chunkContext) -> {
            LocalDate today = LocalDate.now();
            LocalDate oneWeekAgo = today.minusWeeks(1);

            LocalDateTime start = oneWeekAgo.atStartOfDay();
            LocalDateTime end = today.atStartOfDay();

            long count = chatHistoryRepository.countByTypeAndTimestampBetween(ChatType.USER, start, end);

            ChatStatistic chatStatistic = ChatStatistic.builder()
                    .statDate(today)
                    .chatStatType(WEEKLY_CHAT_MESSAGE_COUNT)
                    .value(count)
                    .build();

            chatStatisticRepository.save(chatStatistic);

            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Job weeklyMessageUserJob() {
        return new JobBuilder("weeklyMessageUserJob", jobRepository)
                .start(weeklyMessageUserStep())
                .build();
    }

    @Bean
    public Step weeklyMessageUserStep() {
        return new StepBuilder("weeklyMessageUserStep", jobRepository)
                .tasklet(weeklyMessageUserCountTasklet(), platformTransactionManager)
                .build();
    }

    private Tasklet weeklyMessageUserCountTasklet() {
        return (contribution, chunkContext) -> {
            LocalDate today = LocalDate.now();
            LocalDate oneWeekAgo = today.minusWeeks(1);

            LocalDateTime start = oneWeekAgo.atStartOfDay();
            LocalDateTime end = today.atStartOfDay();

            Set<String> set = chatHistoryRepository.findAllByTypeAndTimestampBetween(ChatType.USER, start, end)
                    .stream()
                    .map(ChatHistory::getConversationId)
                    .collect(Collectors.toSet());

            ChatStatistic chatStatistic = ChatStatistic.builder()
                    .statDate(today)
                    .chatStatType(WEEKLY_CHAT_ACTIVE_USER_COUNT)
                    .value((long) set.size())
                    .build();

            chatStatisticRepository.save(chatStatistic);

            return RepeatStatus.FINISHED;
        };
    }
}
