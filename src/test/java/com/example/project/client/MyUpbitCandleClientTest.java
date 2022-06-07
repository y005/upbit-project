package com.example.project.client;

import com.example.project.enums.MarketType;
import com.trader.common.enums.MinuteType;
import com.trader.common.utils.MinuteCandle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class MyUpbitCandleClientTest {
    @Autowired
    private UpbitCandleClient upbitCandleClient;

    @Test
    void test() {
        List<MinuteCandle> minuteCandleList = upbitCandleClient.getMinuteCandle(MinuteType.FIVE, MarketType.KRW_BTC,1, LocalDateTime.now());
        assertThat(minuteCandleList.size(), is(1));
    }
}