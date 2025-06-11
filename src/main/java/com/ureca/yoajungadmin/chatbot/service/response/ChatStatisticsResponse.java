package com.ureca.yoajungadmin.chatbot.service.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatStatisticsResponse {
    private List<ChatStatDto> dailyActiveUsers;
    private List<ChatStatDto> weeklyActiveUsers;
    private List<ChatStatDto> monthlyActiveUsers;
    private List<ChatStatDto> dailyMessageCounts;
    private List<ChatStatDto> weeklyMessageCounts;
    private List<ChatStatDto> monthlyMessageCounts;
}
