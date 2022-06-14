package com.example.project.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "slack")
public class SlackConfig {
    private String assetsChannel;
    private String orderChannel;
    private String backtestChannel;
    private String channelUrl;
    private String chatUrl;
    private String accessKey;
    private String webhookUrl;
}
