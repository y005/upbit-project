package com.example.project.service;

import com.example.project.client.UpbitCrawlClient;
import com.example.project.entity.Btc5MinuteCandle;
import com.example.project.enums.MarketType;
import com.example.project.repository.Btc5MinuteCandleRepository;
import com.trader.common.enums.MinuteType;
import com.trader.common.utils.MinuteCandle;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class UpbitCrawlerService {
    private final Btc5MinuteCandleRepository btc5MinuteCandleRepository;
    private final UpbitCrawlClient upbitCandleClient;

    public void saveCoin5MinCandleInfo(MarketType marketType, int count, LocalDateTime localDateTime) {
        List<MinuteCandle> result = upbitCandleClient.getMinuteCandle(MinuteType.FIVE, marketType, count, localDateTime);
        result.stream()
                .map((e)-> new Btc5MinuteCandle(e.getCandleDateTimeKst(), e.getOpeningPrice(), e.getTradePrice(), e.getHighPrice(), e.getLowPrice(), e.getCandleAccTradeVolume()))
                .forEach(btc5MinuteCandleRepository::save);
    }

    public void saveCoin5MinCandleInfo(MarketType marketType, LocalDateTime localDateTime) {
        List<MinuteCandle> result = new ArrayList<>();
        int dayOfMonth = localDateTime.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
        LocalDateTime time = LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(), 1, 3, 0);
        for (int i = 1; i < dayOfMonth + 1; i++) {
            result.addAll(upbitCandleClient.getMinuteCandle(MinuteType.FIVE, marketType, 144, time));
            time = time.plusHours(12);
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            result.addAll(upbitCandleClient.getMinuteCandle(MinuteType.FIVE, marketType, 144, time));
            time = time.plusHours(12);
        }
        result.stream()
                .map((e)-> new Btc5MinuteCandle(e.getCandleDateTimeKst(), e.getOpeningPrice(), e.getTradePrice(), e.getHighPrice(), e.getLowPrice(), e.getCandleAccTradeVolume()))
                .forEach(btc5MinuteCandleRepository::save);
    }

    public MinuteCandle getCurrent5MinCandleInfo(MarketType marketType) {
        return upbitCandleClient.getMinuteCandle(MinuteType.FIVE, marketType, 1, LocalDateTime.now()).get(0);
    }
}
