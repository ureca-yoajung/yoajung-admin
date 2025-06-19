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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.ureca.yoajungadmin.chatbot.entity.ChatStatType.DAILY_CHAT_ACTIVE_USER_COUNT;
import static com.ureca.yoajungadmin.chatbot.entity.ChatStatType.DAILY_CHAT_MESSAGE_COUNT;

@Configuration
@RequiredArgsConstructor
public class ChatDailyBatch {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final ChatHistoryRepository chatHistoryRepository;
    private final ChatStatisticRepository chatStatisticRepository;

    @Bean
    public Job dailyMessageJob() {
        return new JobBuilder("dailyMessageJob", jobRepository)
                .start(dailyMessageStep())
                .build();
    }

    @Bean
    public Step dailyMessageStep() {
        return new StepBuilder("dailyMessageStep", jobRepository)
                .tasklet(dailyMessageCountTasklet(), platformTransactionManager)
                .build();
    }

    private Tasklet dailyMessageCountTasklet() {
        return (contribution, chunkContext) -> {
            LocalDate statDate = LocalDate.now().minusDays(1);
            LocalDateTime start = statDate.atStartOfDay();
            LocalDateTime end = start.plusDays(1);

            long count = chatHistoryRepository.countByTypeAndTimestampBetween(ChatType.USER, start, end);

            ChatStatistic chatStatistic = ChatStatistic.builder()
                    .statDate(LocalDate.now())
                    .chatStatType(DAILY_CHAT_MESSAGE_COUNT)
                    .value(count)
                    .build();

            chatStatisticRepository.save(chatStatistic);

            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Job dailyMessageUserJob() {
        return new JobBuilder("dailyMessageUserJob", jobRepository)
                .start(dailyMessageUserStep())
                .build();
    }

    @Bean
    public Step dailyMessageUserStep() {
        return new StepBuilder("dailyMessageUserStep", jobRepository)
                .tasklet(dailyMessageUserCountTasklet(), platformTransactionManager)
                .build();
    }

    private Tasklet dailyMessageUserCountTasklet() {
        return (contribution, chunkContext) -> {
            LocalDate statDate = LocalDate.now().minusDays(1);
            LocalDateTime start = statDate.atStartOfDay();
            LocalDateTime end = start.plusDays(1);

            List<ChatHistory> chatMemoryList = chatHistoryRepository.findAllByTypeAndTimestampBetween(ChatType.USER, start, end);

            Set<String> set = new HashSet<>();

            for (ChatHistory chatHistory : chatMemoryList)
                set.add(chatHistory.getConversationId());

            ChatStatistic chatStatistic = ChatStatistic.builder()
                    .statDate(LocalDate.now())
                    .chatStatType(DAILY_CHAT_ACTIVE_USER_COUNT)
                    .value((long) set.size())
                    .build();

            chatStatisticRepository.save(chatStatistic);

            return RepeatStatus.FINISHED;
        };
    }

}
