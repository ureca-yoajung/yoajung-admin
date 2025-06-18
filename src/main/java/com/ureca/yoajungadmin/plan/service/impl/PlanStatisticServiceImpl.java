package com.ureca.yoajungadmin.plan.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ureca.yoajungadmin.common.BaseCode;
import com.ureca.yoajungadmin.plan.entity.PlanStatistic;
import com.ureca.yoajungadmin.plan.entity.enums.PlanCategory;
import com.ureca.yoajungadmin.plan.exception.PlanStatisticNotFoundException;
import com.ureca.yoajungadmin.plan.repository.PlanStatisticRepository;
import com.ureca.yoajungadmin.plan.service.PlanStatisticService;
import com.ureca.yoajungadmin.plan.service.response.PlanStatisticInfoResponse;
import com.ureca.yoajungadmin.plan.service.response.PlanStatisticResponse;
import com.ureca.yoajungadmin.user.entity.AgeGroup;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PlanStatisticServiceImpl implements PlanStatisticService {

    private final PlanStatisticRepository planStatisticRepository;
    private final ObjectMapper objectMapper;
    private final ChatClient chatClient;

    @Value("${spring.ai.chat.system-prompt1}")
    private Resource promptRes;

    private String prompt;

    @PostConstruct
    public void init() throws IOException {
        prompt = readPrompt(promptRes);
    }

    private String readPrompt(Resource resource) throws IOException {
        return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }

    @Override
    public PlanStatisticInfoResponse getPlanStatistic() throws JsonProcessingException {
        PlanStatistic latestStatistic = planStatisticRepository
                .findTopByOrderByCreateDateDesc()
                .orElseThrow(() -> new PlanStatisticNotFoundException(BaseCode.PLAN_STATISTICS_NOT_FOUND));

        LocalDateTime startOfDay = latestStatistic.getCreateDate().toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        List<PlanStatisticResponse> responses = new ArrayList<>();

        for (AgeGroup ageGroup : AgeGroup.values()) {
            if (ageGroup != AgeGroup.ALL) {
                responses.add(createStatisticResponseForAgeGroup(ageGroup, startOfDay, endOfDay));
            }
        }

        String data = objectMapper.writeValueAsString(responses);
        String content = chatClient.prompt()
                .system(prompt)
                .options(ChatOptions.builder()
                        .model("gpt-4.1-mini")
                        .temperature(0.5)
                        .build())
                .user(data)
                .call()
                .content();

        System.out.println(content);

        return new PlanStatisticInfoResponse(responses, content);
    }

    private PlanStatisticResponse createStatisticResponseForAgeGroup(AgeGroup ageGroup, LocalDateTime start, LocalDateTime end) {
        List<PlanStatistic> stats = planStatisticRepository
                .findAllByAgeGroupAndPlanCategoryAndCreateDateBetween(ageGroup, PlanCategory.ALL, start, end);

        long totalUserCount = stats.stream()
                .mapToLong(PlanStatistic::getUserCount)
                .sum();

        return new PlanStatisticResponse(ageGroup, totalUserCount);
    }
}
