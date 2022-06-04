package com.example.project.service;

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
    void save() {
        upbitService.saveBtcInfo(LocalDateTime.now());
    }

    @Test
    @DisplayName("비트코인 5분봉 크롤링해서 데이터 디비에 저장하기(2022년 05월 01일 00:00 부터 2022년 05월 31일 23:55)")
    void saveBtcInfo() {
        int apiCall = 0;
        int j;
        boolean flag;

        for (int i = 30; i < 31; ++i) {
            flag = true;
            j = 15;
            while (flag || (j != 15)) {
                flag = false;
                for (int k = 5; k < 60; k += 5){
                    try {
                        upbitService.saveBtcInfo(LocalDateTime.of(2022, Month.APRIL, i, j, k, 0));
                        apiCall = (apiCall + 1) % 10;
                        if (apiCall == 0) {
                            Thread.sleep(1000);
                        }
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                j = (j + 1) % 24;
            }
        }
        apiCall = 0;
        for (int i = 1; i < 32; ++i) {
            flag = true;
            j = 15;
            while (flag || (j != 15)) {
                flag = false;
                for (int k = 5; k < 60; k += 5){
                    try {
                        upbitService.saveBtcInfo(LocalDateTime.of(2022, Month.MAY, i, j, k, 0));
                        apiCall = (apiCall + 1) % 10;
                        if (apiCall == 0) {
                            Thread.sleep(1000);
                        }
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                j = (j + 1) % 24;
            }
        }
    }
}