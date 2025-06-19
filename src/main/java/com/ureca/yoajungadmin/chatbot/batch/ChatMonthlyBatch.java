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

import static com.ureca.yoajungadmin.chatbot.entity.ChatStatType.*;

@Configuration
@RequiredArgsConstructor
public class ChatMonthlyBatch {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final ChatHistoryRepository chatHistoryRepository;
    private final ChatStatisticRepository chatStatisticRepository;

    @Bean
    public Job monthlyMessageJob() {
        return new JobBuilder("monthlyMessageJob", jobRepository)
                .start(monthlyMessageStep())
                .build();
    }

    @Bean
    public Step monthlyMessageStep() {
        return new StepBuilder("monthlyMessageStep", jobRepository)
                .tasklet(monthlyMessageCountTasklet(), platformTransactionManager)
                .build();
    }

    private Tasklet monthlyMessageCountTasklet() {
        return (contribution, chunkContext) -> {
            LocalDate today = LocalDate.now();
            LocalDate oneMonthAgo = today.minusMonths(1);

            LocalDateTime start = oneMonthAgo.atStartOfDay();
            LocalDateTime end = today.plusDays(1).atStartOfDay();

            long count = chatHistoryRepository.countByTypeAndTimestampBetween(ChatType.USER, start, end);

            ChatStatistic chatStatistic = ChatStatistic.builder()
                    .statDate(today)
                    .chatStatType(MONTHLY_CHAT_MESSAGE_COUNT)
                    .value(count)
                    .build();

            chatStatisticRepository.save(chatStatistic);

            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Job monthlyMessageUserJob() {
        return new JobBuilder("monthlyMessageUserJob", jobRepository)
                .start(monthlyMessageUserStep())
                .build();
    }

    @Bean
    public Step monthlyMessageUserStep() {
        return new StepBuilder("monthlyMessageUserStep", jobRepository)
                .tasklet(monthlyMessageUserCountTasklet(), platformTransactionManager)
                .build();
    }

    private Tasklet monthlyMessageUserCountTasklet() {
        return (contribution, chunkContext) -> {
            LocalDate today = LocalDate.now();
            LocalDate oneMonthAgo = today.minusMonths(1);

            LocalDateTime start = oneMonthAgo.atStartOfDay();
            LocalDateTime end = today.plusDays(1).atStartOfDay();

            Set<String> set = chatHistoryRepository.findAllByTypeAndTimestampBetween(ChatType.USER, start, end)
                    .stream()
                    .map(ChatHistory::getConversationId)
                    .collect(Collectors.toSet());

            ChatStatistic chatStatistic = ChatStatistic.builder()
                    .statDate(today)
                    .chatStatType(MONTHLY_CHAT_ACTIVE_USER_COUNT)
                    .value((long) set.size())
                    .build();

            chatStatisticRepository.save(chatStatistic);

            return RepeatStatus.FINISHED;
        };
    }
}
