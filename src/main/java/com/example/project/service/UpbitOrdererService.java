package com.example.project.service;

import com.example.project.client.InvestmentStrategy;
import com.example.project.client.UpbitBacktestClient;
import com.example.project.client.UpbitOrderClient;
import com.example.project.dto.OrderResult;
import com.example.project.enums.CoinType;
import com.example.project.enums.MarketType;
import com.example.project.enums.TradeType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class UpbitOrdererService {
    private final UpbitBacktestClient upbitBacktestClient;
    private final UpbitOrderClient upbitOrderClient;
    private final InvestmentStrategy investmentStrategy;

    public void trade(MarketType marketType) {
        TradeType tradeType = investmentStrategy.makeDecision();
        log.info("매매 판단 결과: {}", tradeType.getType());
        switch (tradeType) {
            case BID -> buyAll(marketType);
            case ASK -> sellAll(marketType);
        }
    }

    public void buyAll(MarketType marketType) {
        double money = upbitBacktestClient.getMoney() * 0.9;
        log.info("매수금: {}", money);
        buy(marketType, money);
    }

    public OrderResult buy(MarketType marketType, double price) {
        return upbitOrderClient.bid(marketType, price);
    }

    public void sellAll(MarketType marketType) {
        double volume = upbitBacktestClient.getCoinVolume(CoinType.toCoinType(marketType));
        log.info("매도량: {}", volume);
        sell(marketType, volume);
    }

    public OrderResult sell(MarketType marketType, double volume) {
        return upbitOrderClient.ask(marketType, volume);
    }
}
