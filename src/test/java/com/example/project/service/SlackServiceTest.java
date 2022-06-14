package com.example.project.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SlackServiceTest {
    @Autowired
    private SlackService slackService;

    @Test
    void sendAssetsInfo() {
        slackService.sendAssetsInfo("자산 메세지");
    }

    @Test
    void sendOrderInfo() {
        slackService.sendOrderInfo("주문 메세지");
    }

    @Test
    void sendBacktestInfo() {
        slackService.sendBacktestInfo("백테스트 메세지");
    }
}