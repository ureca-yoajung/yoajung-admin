package com.ureca.yoajungadmin.chatbot.entity;

import com.ureca.yoajungadmin.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatStatistic extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate statDate;

    @Enumerated(EnumType.STRING)
    private ChatStatType chatStatType;

    private Long value;

    @Builder
    public ChatStatistic(LocalDate statDate, ChatStatType chatStatType, Long value) {
        this.statDate = statDate;
        this.chatStatType = chatStatType;
        this.value = value;
    }
}
