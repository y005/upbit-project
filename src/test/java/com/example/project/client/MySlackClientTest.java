package com.example.project.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MySlackClientTest {
    @Autowired
    private SlackClient slackClient;

    @Test
    void sendMessageUsingWebhook() {
        slackClient.sendMessageUsingWebhook("hello world");
    }
}