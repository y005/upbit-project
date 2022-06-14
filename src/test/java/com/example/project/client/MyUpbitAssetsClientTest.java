package com.example.project.client;

import com.example.project.dto.UpbitAsset;
import com.example.project.enums.CoinType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class MyUpbitAssetsClientTest {
    @Autowired
    private UpbitAssetsClient upbitAssetsClient;

    @Test
    void getUpbitWalletKrw() {
        Map<String, UpbitAsset> wallet = upbitAssetsClient.getUpbitWallet();
        UpbitAsset krw = wallet.get("KRW");

        assertThat(krw.getCurrency(), is("KRW"));
        System.out.println(krw.getBalance());
    }

    @Test
    void getUpbitWalletBtc() {
        Map<String, UpbitAsset> wallet = upbitAssetsClient.getUpbitWallet();
        UpbitAsset btc = wallet.get("BTC");

        assertThat(btc.getCurrency(), is("BTC"));
        System.out.println(btc.getBalance());
    }

    @Test
    void getMoney() {
        System.out.println(upbitAssetsClient.getMoney());
    }

    @Test
    void getCoinVolume() {
        System.out.println(upbitAssetsClient.getCoinVolume(CoinType.BTC));
    }
}