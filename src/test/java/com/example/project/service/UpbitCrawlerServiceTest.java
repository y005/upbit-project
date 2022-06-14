package com.example.project.service;

import com.example.project.enums.MarketType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.Month;

@SpringBootTest
class UpbitCrawlerServiceTest {
    @Autowired
    private UpbitCrawlerService upbitService;

    @Test
    @DisplayName("비트코인 최근 한달 데이터 저장하기")
    void saveBtcInfo() {
        upbitService.saveCoin5MinCandleInfoBefore1Month(MarketType.KRW_BTC);
    }

    @Test
    @DisplayName("비트코인 최근 1시간 데이터 저장하기")
    void saveBtc1HourInfo() {
        upbitService.saveCoin5MinCandleInfoBefore1Hour(MarketType.KRW_BTC, LocalDateTime.now());
    }

    @Test
    void getRecent5MinCandleInfo() {
        System.out.println(upbitService.getRecent5MinCandleInfo(MarketType.KRW_BTC));
    }
}