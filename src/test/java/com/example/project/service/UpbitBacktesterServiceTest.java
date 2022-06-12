package com.example.project.service;

import com.example.project.entity.Assets;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UpbitBacktesterServiceTest {
    @Autowired
    private UpbitBacktesterService upbitBacktesterService;

    @Test
    void updateWallet() {
        Assets assets = upbitBacktesterService.updateWallet();
        System.out.println(assets.toString());
    }

    @Test
    void sendWalletInfo() {
        upbitBacktesterService.sendWalletInfo();
    }
}