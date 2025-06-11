package com.ureca.yoajungadmin.chatbot.service.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatStatDto {
    private LocalDate statDate;
    private Long value;
}
