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
@Table(name = "chatStatistic")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatStatistic extends BaseTimeEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "statDate")
    private LocalDate statDate;

    @Column(name = "chatStatType")
    @Enumerated(EnumType.STRING)
    private ChatStatType chatStatType;

    @Column(name = "value")
    private Long value;

    @Builder
    public ChatStatistic(LocalDate statDate, ChatStatType chatStatType, Long value) {
        this.statDate = statDate;
        this.chatStatType = chatStatType;
        this.value = value;
    }
}
