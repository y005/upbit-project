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
    @DisplayName("비트코인 5분봉 크롤링해서 데이터 디비에 저장하기(2022년 05월 01일 00:00 부터 2022년 05월 31일 23:55)")
    void saveBtcInfo() {
        upbitService.saveCoin5MinCandleInfo(MarketType.KRW_BTC, LocalDateTime.of(2022, Month.MAY, 1, 0, 0));
    }

    @Test
    @DisplayName("1시간 전 캔들차트 가져오기")
    void saveBtc1HourInfo() {
        upbitService.saveCoin5MinCandleInfoBefore1Hour(MarketType.KRW_BTC, LocalDateTime.now());
    }
}