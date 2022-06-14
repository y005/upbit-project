package com.example.project.client;

import com.example.project.enums.MarketType;
import com.example.project.service.UpbitCrawlerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MyUpbitBacktestClientTest {
    @Autowired
    private UpbitBacktestClient upbitBacktestClient;

    @Autowired
    private UpbitCrawlerService upbitCrawlerService;

    @Test
    @DisplayName("과거 한 달 데이터에 대한 예측 수익율 계산")
    void testInvestmentStrategy() {
        upbitCrawlerService.saveCoin5MinCandleInfoBefore1Month(MarketType.KRW_BTC);
        double result = upbitBacktestClient.testInvestmentStrategy(2, 5);
        System.out.println("예상 수익률: " + String.format("%.2f",result));
    }
}