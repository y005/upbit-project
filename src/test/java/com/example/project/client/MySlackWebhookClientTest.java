package com.example.project.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MySlackWebhookClientTest {
    @Autowired
    private SlackWebhookClient slackWebhookClient;

    @Test
    void sendMessage() {
        slackWebhookClient.sendMessage("hello world");
    }

}