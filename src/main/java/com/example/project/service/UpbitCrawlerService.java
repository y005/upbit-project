package com.example.project.service;

import com.example.project.client.MarketType;
import com.example.project.client.UpbitCandleClient;
import com.example.project.entity.Btc5MinuteCandle;
import com.example.project.repository.Btc5MinuteCandleRepository;
import com.trader.common.enums.MinuteType;
import com.trader.common.utils.MinuteCandle;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UpbitCrawlerService {
    private final Btc5MinuteCandleRepository btc5MinuteCandleRepository;
    private final UpbitCandleClient upbitCandleClient;

    public void saveBtcInfo(LocalDateTime localDateTime) {
        int year = localDateTime.getYear();
        int month = localDateTime.getDayOfMonth();
        int day = localDateTime.getDayOfMonth();
        int hour = localDateTime.getHour();
        int min = localDateTime.getMinute();

        List<MinuteCandle> result = upbitCandleClient.getMinuteCandle(MinuteType.FIVE, MarketType.KRW_BTC, 1, LocalDateTime.of(year, month+1, day, hour, min, 0));
        System.out.println(LocalDateTime.of(year, month+1, day, hour, min, 0));
        result.stream()
                .map((e)-> new Btc5MinuteCandle(e.getCandleDateTimeKst(), e.getOpeningPrice(), e.getTradePrice(), e.getHighPrice(), e.getLowPrice(), e.getCandleAccTradeVolume()))
                .forEach(btc5MinuteCandleRepository::save);
    }
}
