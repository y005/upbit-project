package com.example.project.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UpbitBacktesterServiceTest {
    @Autowired
    private UpbitBacktesterService upbitBacktesterService;

    @Test
    void backtesting() {
        System.out.println(upbitBacktesterService.backtesting());
    }
}