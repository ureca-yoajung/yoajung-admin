package com.ureca.yoajungadmin.summary.client.dto;

import lombok.Data;

import java.util.Map;

/**
 * Dify Completion API request payload:
 * {
 *   "inputs": {...},
 *   "response_mode": "blocking",
 *   "user": "yoajung-admin" (optional)
 * }
 */
@Data
public class DifyRequest {
    private Map<String, String> inputs;
    private String response_mode = "blocking";
    private String user = "system";

    public DifyRequest(Map<String, String> inputs) {
        this.inputs = inputs;
    }
}
