package com.example.project.service;

import com.example.project.client.*;
import com.example.project.dto.OrderResult;
import com.example.project.entity.Btc5MinuteCandle;
import com.example.project.enums.CoinType;
import com.example.project.enums.MarketType;
import com.example.project.enums.TradeType;
import com.example.project.repository.Btc5MinuteCandleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class UpbitOrdererService {
    private final Btc5MinuteCandleRepository btc5MinuteCandleRepository;
    private final InvestStrategyService investStrategyService;
    private final UpbitAssetsClient upbitAssetsClient;
    private final UpbitCrawlerService upbitCrawlerService;
    private final UpbitOrderClient upbitOrderClient;
    private final SlackService slackService;

    public void trade(MarketType marketType) {
        List<Btc5MinuteCandle> OneHourInfo = btc5MinuteCandleRepository.findBtc5MinuteCandlesByTimeGreaterThanOrderByTimeDesc(LocalDateTime.now().minusHours(1));
        TradeType tradeType = investStrategyService.makeDecision(OneHourInfo);
        switch (tradeType) {
            case BID -> {
                buyAll(marketType);
                log.info("매매 판단 결과: {}", tradeType.getType());
            }
            case ASK -> {
                if (upbitAssetsClient.haveBtc() && isProfitable(marketType)) {
                    sellAll(marketType);
                    log.info("매매 판단 결과: {}", tradeType.getType());
                }
            }
            default -> log.info("매매 판단 결과: {}", tradeType.getType());
        }
    }

    private boolean isProfitable(MarketType marketType) {
        CoinType coinType = CoinType.toCoinType(marketType);
        double avgPrice = upbitAssetsClient.getCoinAvgPrice(coinType);
        double tradePrice = upbitCrawlerService.getRecent5MinCandleInfo(marketType).getTradePrice();
        return tradePrice > avgPrice;
    }

    public void buyAll(MarketType marketType) {
        double money = upbitAssetsClient.getMoney() * 0.9;
        slackService.sendOrderInfo("코인 매수금 - " + money + "원");
        log.info("매수금: {}", money);
        buy(marketType, money);
    }

    public OrderResult buy(MarketType marketType, double price) {
        return upbitOrderClient.bid(marketType, price);
    }

    public void sellAll(MarketType marketType) {
        double volume = upbitAssetsClient.getCoinVolume(CoinType.toCoinType(marketType));
        slackService.sendOrderInfo("코인 매도량 - " + volume);
        log.info("매도량: {}", volume);
        sell(marketType, volume);
    }

    public OrderResult sell(MarketType marketType, double volume) {
        return upbitOrderClient.ask(marketType, volume);
    }
}
