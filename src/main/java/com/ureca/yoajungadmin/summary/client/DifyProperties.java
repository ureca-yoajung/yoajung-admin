package com.ureca.yoajungadmin.summary.client;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "dify")
public class DifyProperties {
    private String baseUrl;
    private String apiKey;
    private String appId;
}
