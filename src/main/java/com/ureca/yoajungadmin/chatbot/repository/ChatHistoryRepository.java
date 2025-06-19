package com.ureca.yoajungadmin.chatbot.repository;

import com.ureca.yoajungadmin.chatbot.entity.ChatHistory;
import com.ureca.yoajungadmin.chatbot.entity.ChatMemory;
import com.ureca.yoajungadmin.chatbot.entity.ChatType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatHistoryRepository extends JpaRepository<ChatHistory, Long> {
    long countByTypeAndTimestampBetween(ChatType type, LocalDateTime start, LocalDateTime end);
    List<ChatMemory> findAllByTypeAndTimestampBetween(ChatType type, LocalDateTime start, LocalDateTime end);
}
