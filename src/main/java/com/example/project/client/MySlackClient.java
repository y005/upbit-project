package com.example.project.client;

import com.example.project.annotation.SlackErrorHandler;
import com.example.project.config.SlackConfig;
import com.example.project.dto.SlackChannel;
import com.example.project.dto.SlackChannelApiResult;
import com.example.project.dto.SlackChannelMessage;
import com.example.project.dto.SlackMessage;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@Component
@AllArgsConstructor
public class MySlackClient implements SlackClient {
    private final SlackConfig slackConfig;
    private final RestTemplate restTemplate;

    @Override
    @SlackErrorHandler
    public void sendMessageUsingWebhook(Object data) {
        SlackMessage slackMessage = new SlackMessage(data);
        requestMessage(makeSlackUrl(slackConfig.getWebhookUrl()), slackMessage);
    }

    @Override
    @SlackErrorHandler
    public void sendMessageUsingOauth(Object data, String channel) {
        SlackChannelMessage slackChannelMessage = new SlackChannelMessage(data);
        String accessToken = "Bearer " + slackConfig.getAccessKey();
        SlackChannelApiResult results = requestChannelList(makeSlackUrl(slackConfig.getChannelUrl()), accessToken);
        SlackChannel result = results.getChannels().stream().filter((ele)-> ele.getName().equals(channel)).findFirst().orElseThrow(()-> new RuntimeException("존재하지 않는 채널명"));
        slackChannelMessage.setChannel(result.getId());
        requestMessage(makeSlackUrl(slackConfig.getChatUrl()), slackChannelMessage, accessToken);
    }

    private URI makeSlackUrl(String url) {
        return UriComponentsBuilder
                .fromUriString(url)
                .encode(StandardCharsets.UTF_8)
                .buildAndExpand()
                .toUri();
    }

    private SlackChannelApiResult requestChannelList(URI url, String accessToken) {
        RequestEntity<Void> request = RequestEntity.get(url)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken)
                .build();
        ResponseEntity<SlackChannelApiResult> result = restTemplate.exchange(request, SlackChannelApiResult.class);
        return result.getBody();
    }

    private void requestMessage(URI url, SlackChannelMessage slackChannelMessage, String accessToken) {
        RequestEntity<SlackChannelMessage> request = RequestEntity.post(url)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken)
                .body(slackChannelMessage);
        restTemplate.exchange(request, Void.class);
    }

    private void requestMessage(URI url, SlackMessage slackMessage) {
        RequestEntity<SlackMessage> request = RequestEntity.post(url)
                .accept(MediaType.APPLICATION_JSON)
                .body(slackMessage);
        restTemplate.exchange(request, Object.class);
    }
}
