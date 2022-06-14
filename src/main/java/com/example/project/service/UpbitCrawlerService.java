package com.example.project.service;

import com.example.project.annotation.CrawlErrorHandler;
import com.example.project.client.UpbitCrawlClient;
import com.example.project.entity.Btc5MinuteCandle;
import com.example.project.enums.MarketType;
import com.example.project.repository.Btc5MinuteCandleRepository;
import com.trader.common.enums.MinuteType;
import com.trader.common.utils.MinuteCandle;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class UpbitCrawlerService {
    private final Btc5MinuteCandleRepository btc5MinuteCandleRepository;
    private final UpbitCrawlClient upbitCrawlClient;

    @CrawlErrorHandler
    public void saveCoin5MinCandleInfo(MarketType marketType, int count, LocalDateTime localDateTime) {
        List<MinuteCandle> result = upbitCrawlClient.getMinuteCandle(MinuteType.FIVE, marketType, count, localDateTime);
        result.stream()
                .map((e) -> new Btc5MinuteCandle(e.getCandleDateTimeKst(), e.getOpeningPrice(), e.getTradePrice(), e.getHighPrice(), e.getLowPrice(), e.getCandleAccTradeVolume()))
                .forEach(btc5MinuteCandleRepository::save);

    }

    @CrawlErrorHandler
    public void saveCoin5MinCandleInfoBefore1Hour(MarketType marketType, LocalDateTime localDateTime) {
        LocalDateTime before1Hour = localDateTime.minusHours(1);
        List<MinuteCandle> result = upbitCrawlClient.getMinuteCandle(MinuteType.FIVE, marketType, 12, before1Hour);
        result.stream()
                .map((e) -> new Btc5MinuteCandle(e.getCandleDateTimeKst(), e.getOpeningPrice(), e.getTradePrice(), e.getHighPrice(), e.getLowPrice(), e.getCandleAccTradeVolume()))
                .forEach(btc5MinuteCandleRepository::save);
    }

    @CrawlErrorHandler
    public void saveCoin5MinCandleInfoBefore1Month(MarketType marketType) {
        List<MinuteCandle> result = new ArrayList<>();
        LocalDateTime time = LocalDateTime.now().minusDays(30);

        for (int i = 0; i < 30; i++) {
            result.addAll(upbitCrawlClient.getMinuteCandle(MinuteType.FIVE, marketType, 144, time));
            time = time.plusHours(12);
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            result.addAll(upbitCrawlClient.getMinuteCandle(MinuteType.FIVE, marketType, 144, time));
            time = time.plusHours(12);
        }
        result.stream()
                .map((e)-> new Btc5MinuteCandle(e.getCandleDateTimeKst(), e.getOpeningPrice(), e.getTradePrice(), e.getHighPrice(), e.getLowPrice(), e.getCandleAccTradeVolume()))
                .forEach(btc5MinuteCandleRepository::save);
    }

    @CrawlErrorHandler
    public MinuteCandle getRecent5MinCandleInfo(MarketType marketType) {
        return upbitCrawlClient.getMinuteCandle(MinuteType.FIVE, marketType, 1, LocalDateTime.now()).get(0);
    }
}
