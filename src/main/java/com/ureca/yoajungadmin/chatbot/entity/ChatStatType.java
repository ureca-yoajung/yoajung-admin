package com.ureca.yoajungadmin.chatbot.entity;

public enum ChatStatType {
    // 활성 사용자 수 (Active Users)
    DAILY_CHAT_ACTIVE_USER_COUNT,    // 일간 활성 사용자 수
    WEEKLY_CHAT_ACTIVE_USER_COUNT,   // 주간 활성 사용자 수
    MONTHLY_CHAT_ACTIVE_USER_COUNT,  // 월간 활성 사용자 수

    // 채팅 메시지 수 (Chat Message Count)
    DAILY_CHAT_MESSAGE_COUNT,   // 일간 채팅 메시지 수
    WEEKLY_CHAT_MESSAGE_COUNT,  // 주간 채팅 메시지 수
    MONTHLY_CHAT_MESSAGE_COUNT  // 월간 채팅 메시지 수
}
