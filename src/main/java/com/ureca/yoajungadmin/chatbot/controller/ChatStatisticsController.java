package com.ureca.yoajungadmin.chatbot.controller;

import com.ureca.yoajungadmin.chatbot.service.ChatStatisticService;
import com.ureca.yoajungadmin.chatbot.service.response.ChatStatisticsResponse;
import com.ureca.yoajungadmin.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ureca.yoajungadmin.common.BaseCode.CHAT_STATISTICS_READ_SUCCESS;

@RestController
@RequestMapping("/chat-statistics")
@RequiredArgsConstructor
public class ChatStatisticsController {

    private final ChatStatisticService chatStatisticService;

    @GetMapping
    public ResponseEntity<ApiResponse<ChatStatisticsResponse>> getChatStatistics() {
        return ResponseEntity.ok(ApiResponse.of(CHAT_STATISTICS_READ_SUCCESS, chatStatisticService.getRecentStats()));
    }
}
