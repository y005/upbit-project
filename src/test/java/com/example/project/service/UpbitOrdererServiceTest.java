package com.example.project.service;

import com.example.project.client.UpbitAssetsClient;
import com.example.project.dto.OrderResult;
import com.example.project.dto.UpbitAsset;
import com.example.project.enums.MarketType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
class UpbitOrdererServiceTest {
    @Autowired
    private UpbitOrdererService upbitOrdererService;

    @Autowired
    private UpbitAssetsClient upbitAssetsClient;

    @Test
    void buy() {
        OrderResult result = upbitOrdererService.buy(MarketType.KRW_BTC, 5000);
        System.out.println(result);
    }

    @Test
    void sell() {
        Map<String, UpbitAsset > wallet = upbitAssetsClient.getUpbitWallet();
        UpbitAsset btc = wallet.get("BTC");

        OrderResult result = upbitOrdererService.sell(MarketType.KRW_BTC, btc.getBalance());
        System.out.println(result);
    }

    @Test
    void buyAll() {
        upbitOrdererService.buyAll(MarketType.KRW_BTC);
    }

    @Test
    void sellAll() {
        upbitOrdererService.sellAll(MarketType.KRW_BTC);
    }
}