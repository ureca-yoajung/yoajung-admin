package com.ureca.yoajungadmin.config;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class AIConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder) throws IOException {
        return chatClientBuilder.build();
    }
}
