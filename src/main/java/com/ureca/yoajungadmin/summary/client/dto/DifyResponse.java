package com.ureca.yoajungadmin.summary.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Dify /completion-messages blocking-mode response.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DifyResponse {

    private String answer;
    private Metadata metadata;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Metadata {

        private Usage usage;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Usage {

        @JsonProperty("prompt_tokens")
        private Integer promptTokens;

        @JsonProperty("completion_tokens")
        private Integer completionTokens;

        @JsonProperty("total_tokens")
        private Integer totalTokens;
    }
}
