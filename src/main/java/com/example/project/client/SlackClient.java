package com.example.project.client;

public interface SlackClient {
    void sendMessageUsingWebhook(Object message);
    void sendMessageUsingOauth(Object message, String channel);
}
