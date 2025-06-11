package com.ureca.yoajungadmin.chatbot.repository;

import com.ureca.yoajungadmin.chatbot.entity.ChatStatType;
import com.ureca.yoajungadmin.chatbot.entity.ChatStatistic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatStatisticRepository extends JpaRepository<ChatStatistic, Long> {

    List<ChatStatistic> findTop7ByChatStatTypeOrderByStatDateDesc(ChatStatType chatStatType);
}
