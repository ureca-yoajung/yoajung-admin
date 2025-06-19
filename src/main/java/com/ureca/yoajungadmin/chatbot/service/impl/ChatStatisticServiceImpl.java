package com.ureca.yoajungadmin.chatbot.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ureca.yoajungadmin.chatbot.entity.ChatStatType;
import com.ureca.yoajungadmin.chatbot.entity.ChatStatistic;
import com.ureca.yoajungadmin.chatbot.repository.ChatStatisticRepository;
import com.ureca.yoajungadmin.chatbot.service.ChatStatisticService;
import com.ureca.yoajungadmin.chatbot.service.response.ChatStatDto;
import com.ureca.yoajungadmin.chatbot.service.response.ChatStatisticsResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.ureca.yoajungadmin.chatbot.entity.ChatStatType.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatStatisticServiceImpl implements ChatStatisticService {
    private final ChatStatisticRepository chatStatisticRepository;
    private final ObjectMapper objectMapper;
    private final ChatClient chatClient;

    @Value("${spring.ai.chat.system-prompt2}")
    private Resource promptRes;

    private String prompt;

    @PostConstruct
    public void init() throws IOException {
        prompt = readPrompt(promptRes);
    }

    private String readPrompt(Resource resource) throws IOException {
        return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }

    @Override
    public ChatStatisticsResponse getRecentStats() throws JsonProcessingException {
        ChatStatisticsResponse chatStatisticsResponse = ChatStatisticsResponse.builder()
                .dailyActiveUsers(fetchTop7ChatStatistics(DAILY_CHAT_ACTIVE_USER_COUNT))
                .weeklyActiveUsers(fetchTop7ChatStatistics(WEEKLY_CHAT_ACTIVE_USER_COUNT))
                .monthlyActiveUsers(fetchTop7ChatStatistics(MONTHLY_CHAT_ACTIVE_USER_COUNT))
                .dailyMessageCounts(fetchTop7ChatStatistics(DAILY_CHAT_MESSAGE_COUNT))
                .weeklyMessageCounts(fetchTop7ChatStatistics(WEEKLY_CHAT_MESSAGE_COUNT))
                .monthlyMessageCounts(fetchTop7ChatStatistics(MONTHLY_CHAT_MESSAGE_COUNT))
                .build();

        String data = objectMapper.writeValueAsString(chatStatisticsResponse);
        String content = chatClient.prompt()
                .system(prompt)
                .options(ChatOptions.builder()
                        .model("gpt-4.1-mini")
                        .temperature(0.5)
                        .build())
                .user(data)
                .call()
                .content();

        chatStatisticsResponse.setContent(content);

        return chatStatisticsResponse;
    }

    private List<ChatStatDto> fetchTop7ChatStatistics(ChatStatType chatStatType) {
        Map<LocalDate, Long> map = chatStatisticRepository
                .findTop7ByChatStatTypeOrderByStatDateDesc(chatStatType)
                .stream()
                .collect(Collectors.toMap(
                        ChatStatistic::getStatDate,
                        ChatStatistic::getValue
                ));

        LocalDate today = LocalDate.now();
        LocalDate baseSunday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDate firstOfMonth = today.withDayOfMonth(1);

        List<ChatStatDto> filled = new ArrayList<>(7);
        for (int i = 6; i >= 0; i--) {
            LocalDate date;
            switch (chatStatType) {
                case DAILY_CHAT_ACTIVE_USER_COUNT, DAILY_CHAT_MESSAGE_COUNT ->
                        date = today.minusDays(i);

                case WEEKLY_CHAT_ACTIVE_USER_COUNT, WEEKLY_CHAT_MESSAGE_COUNT ->
                        date = baseSunday.minusWeeks(i);

                case MONTHLY_CHAT_ACTIVE_USER_COUNT, MONTHLY_CHAT_MESSAGE_COUNT ->
                        date = firstOfMonth.minusMonths(i);

                default -> throw new IllegalArgumentException("Unknown type: " + chatStatType);
            }

            long value = map.getOrDefault(date, 0L);
            filled.add(ChatStatDto.builder()
                    .statDate(date)
                    .value(value)
                    .build());
        }
        return filled;
    }
}
