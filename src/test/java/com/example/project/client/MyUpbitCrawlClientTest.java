package com.example.project.client;

import com.example.project.enums.MarketType;
import com.trader.common.enums.MinuteType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class MyUpbitCrawlClientTest {
    @Autowired
    private UpbitCrawlClient upbitCrawlClient;

    @Test
    void getMinuteCandle() {
        var result = upbitCrawlClient.getMinuteCandle(MinuteType.FIVE, MarketType.KRW_BTC, 1, LocalDateTime.now()).get(0);
        System.out.println(result);
    }
}