package com.example.project.service;

import com.example.project.entity.Assets;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class UpbitAssetsServiceTest {
    @Autowired
    private UpbitAssetsService upbitAssetsService;

    @Test
    void updateWallet() {
        Assets assets = upbitAssetsService.updateWallet();
        System.out.println(assets.toString());
    }

    @Test
    void sendWalletInfo() {
        upbitAssetsService.sendWalletInfo();
    }
}