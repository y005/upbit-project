package com.example.project.client;

import com.example.project.config.SlackConfig;
import com.example.project.dto.SlackMessage;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@Component
@AllArgsConstructor
public class MySlackWebhookClient implements SlackWebhookClient {
    private final SlackConfig slackConfig;
    private final RestTemplate restTemplate;

    @Override
    public void sendMessage(Object data) {
        SlackMessage slackMessage = new SlackMessage(data);
        request(makeSlackWebhookUrl(), slackMessage);
    }

    private URI makeSlackWebhookUrl() {
        return UriComponentsBuilder
                .fromUriString(slackConfig.getUrl())
                .encode(StandardCharsets.UTF_8)
                .buildAndExpand()
                .toUri();
    }

    private void request(URI url, SlackMessage slackMessage) {
        RequestEntity<SlackMessage> request = RequestEntity.post(url)
                .accept(MediaType.APPLICATION_JSON)
                .body(slackMessage);
        restTemplate.exchange(request, Void.class);
    }
}
