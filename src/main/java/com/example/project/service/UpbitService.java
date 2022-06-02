package com.example.project.service;

import com.example.project.client.MarketType;
import com.example.project.client.UpbitCandleClient;
import com.example.project.entity.Btc5MinuteCandle;
import com.example.project.repository.Btc5MinuteCandleRepository;
import com.trader.common.enums.MinuteType;
import com.trader.common.utils.MinuteCandle;

import java.time.LocalDateTime;
import java.util.List;

public class UpbitService {
    private Btc5MinuteCandleRepository btc5MinuteCandleRepository;
    private UpbitCandleClient upbitCandleClient;

    public UpbitService(Btc5MinuteCandleRepository btc5MinuteCandleRepository, UpbitCandleClient upbitCandleClient) {
        this.btc5MinuteCandleRepository = btc5MinuteCandleRepository;
        this.upbitCandleClient = upbitCandleClient;
    }

    public void saveBtcInfo(int Month, int day, int hour, int min) {
        List<MinuteCandle> result = upbitCandleClient.getMinuteCandle(MinuteType.FIVE, MarketType.KRW_BTC, 200, LocalDateTime.now());
        result.stream().map((e)-> new Btc5MinuteCandle()).forEach(
                (e) -> {
                    btc5MinuteCandleRepository.save(e);
                }
        );
    }
}
