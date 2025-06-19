package com.ureca.yoajungadmin.summary.client;


import com.ureca.yoajungadmin.summary.client.dto.DifyRequest;
import com.ureca.yoajungadmin.summary.client.dto.DifyResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Map;

@Component
@Slf4j
public class DifyClient {

    private final DifyProperties props;
    private final WebClient webClient;

    public DifyClient(DifyProperties props, WebClient.Builder builder) {
        this.props = props;
        this.webClient = builder.build();
    }

    public String requestSummary(String planInfo, String reviews) {
        DifyRequest body = new DifyRequest(Map.of(
                "planInfo", planInfo,
                "reviews", reviews
        ));

        DifyResponse resp = webClient.post()
                .uri(props.getBaseUrl() + "/completion-messages")
                .header("Authorization", "Bearer " + props.getApiKey())
                .bodyValue(body)
                .retrieve()
                .bodyToMono(DifyResponse.class)
                .doOnError(e -> {
                    if (e instanceof WebClientResponseException ex) {
                        log.error("Dify error body={}", ex.getResponseBodyAsString());
                    }
                })
                .block(); // 동기 호출

        // 예외 타임아웃 처리 추가 필요
        return resp != null ? resp.getAnswer() : "";
    }
}