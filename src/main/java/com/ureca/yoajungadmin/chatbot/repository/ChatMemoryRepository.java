package com.ureca.yoajungadmin.chatbot.repository;

import com.ureca.yoajungadmin.chatbot.entity.ChatMemory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatMemoryRepository extends JpaRepository<ChatMemory, Long> {
    long countByTypeAndTimestampBetween(String type, LocalDateTime start, LocalDateTime end);
    List<ChatMemory> findAllByTypeAndTimestampBetween(String type, LocalDateTime start, LocalDateTime end);
}
