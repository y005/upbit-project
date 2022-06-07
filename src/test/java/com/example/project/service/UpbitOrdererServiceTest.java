package com.example.project.service;

import com.example.project.client.UpbitBacktestClient;
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
    private UpbitBacktestClient upbitBacktestClient;

    @Test
    void testBuy() {
        OrderResult result = upbitOrdererService.buy(MarketType.KRW_BTC, 5000);
        System.out.println(result);
    }

    @Test
    void testSell() {
        Map<String, UpbitAsset > wallet = upbitBacktestClient.getUpbitWallet();
        UpbitAsset btc = wallet.get("BTC");

        OrderResult result = upbitOrdererService.sell(MarketType.KRW_BTC, btc.getBalance());
        System.out.println(result);
    }
}