package com.example.project.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "upbit.api")
public class UpbitConfig {
    private String serverUrl;
    private String accessKey;
    private String secretKey;
}
