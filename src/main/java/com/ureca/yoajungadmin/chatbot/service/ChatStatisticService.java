package com.ureca.yoajungadmin.chatbot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ureca.yoajungadmin.chatbot.service.response.ChatStatisticsResponse;

public interface ChatStatisticService {
    ChatStatisticsResponse getRecentStats() throws JsonProcessingException;
}
