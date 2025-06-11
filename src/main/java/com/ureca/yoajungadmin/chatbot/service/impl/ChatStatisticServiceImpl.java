package com.ureca.yoajungadmin.chatbot.service.impl;

import com.ureca.yoajungadmin.chatbot.entity.ChatStatType;
import com.ureca.yoajungadmin.chatbot.entity.ChatStatistic;
import com.ureca.yoajungadmin.chatbot.repository.ChatStatisticRepository;
import com.ureca.yoajungadmin.chatbot.service.ChatStatisticService;
import com.ureca.yoajungadmin.chatbot.service.response.ChatStatDto;
import com.ureca.yoajungadmin.chatbot.service.response.ChatStatisticsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public ChatStatisticsResponse getRecentStats() {
        return ChatStatisticsResponse.builder()
                .dailyActiveUsers(fetchTop7ChatStatistics(DAILY_CHAT_ACTIVE_USER_COUNT))
                .weeklyActiveUsers (fetchTop7ChatStatistics(WEEKLY_CHAT_ACTIVE_USER_COUNT))
                .monthlyActiveUsers(fetchTop7ChatStatistics(MONTHLY_CHAT_ACTIVE_USER_COUNT))
                .dailyMessageCounts  (fetchTop7ChatStatistics(DAILY_CHAT_MESSAGE_COUNT))
                .weeklyMessageCounts (fetchTop7ChatStatistics(WEEKLY_CHAT_MESSAGE_COUNT))
                .monthlyMessageCounts(fetchTop7ChatStatistics(MONTHLY_CHAT_MESSAGE_COUNT))
                .build();
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
