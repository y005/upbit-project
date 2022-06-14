package com.example.project.client;

import com.example.project.entity.Btc5MinuteCandle;
import com.example.project.enums.TradeType;
import com.example.project.repository.Btc5MinuteCandleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class MyUpbitBacktestClient implements UpbitBacktestClient {
    private final InvestmentStrategy investmentStrategy;
    private final Btc5MinuteCandleRepository btc5MinuteCandleRepository;

    private static class MockAssets {
        public double money;
        public double avgBuyPrice;
        public double balance;

        public MockAssets(double money, double avgBuyPrice, double balance) {
            this.money = money;
            this.avgBuyPrice = avgBuyPrice;
            this.balance = balance;
        }
    }

    public double testInvestmentStrategy(int DROP_CNT, int DROP_RATE) {
        List<Btc5MinuteCandle> oneHourInfo = new ArrayList<>();
        Btc5MinuteCandle currentChart = null;
        TradeType tradeType = null;
        MockAssets mockAssets = new MockAssets(1000000, 0, 0);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime time = now.minusDays(30);

        for (; time.isBefore(now); time = time.plusMinutes(5)) {
            oneHourInfo = btc5MinuteCandleRepository.findBtc5MinuteCandlesByTimeBetweenOrderByTimeDesc(time, time.plusHours(1));
            if (oneHourInfo.isEmpty()) {break;}
            currentChart = oneHourInfo.get(0);
            tradeType = investmentStrategy.makeDecision(oneHourInfo, DROP_CNT, DROP_RATE);
            switch (tradeType) {
                case BID -> {
                    log.info("{} - 매매 판단 결과: {}", time, tradeType.getType());
                    simulateBuyAll(mockAssets, currentChart);
                }
                case ASK -> {
                    if (haveBtc(mockAssets) && isProfitable(mockAssets, currentChart)) {
                        log.info("{} - 매매 판단 결과: {}", time, tradeType.getType());
                        simulateSellAll(mockAssets, currentChart);
                    }
                }
            }
        }
        return calculateProfitRatio(mockAssets, currentChart);
    }

    private boolean haveBtc(MockAssets mockAssets) {
        return mockAssets.balance > 0d;
    }

    private boolean isProfitable(MockAssets mockAssets, Btc5MinuteCandle currentChart) {
        return mockAssets.avgBuyPrice < currentChart.getTradePrice();
    }

    private void simulateBuyAll(MockAssets mockAssets, Btc5MinuteCandle currentChart) {
        double plusBalance = mockAssets.money / currentChart.getTradePrice();
        double prevInvest = mockAssets.avgBuyPrice * mockAssets.balance;
        log.info("매수 정보 - 시가: {}, 비용: {}", currentChart.getTradePrice(), mockAssets.money);
        mockAssets.money -= plusBalance * currentChart.getTradePrice();
        mockAssets.avgBuyPrice = (prevInvest + plusBalance * currentChart.getTradePrice()) / (mockAssets.balance + plusBalance);
        mockAssets.balance = mockAssets.balance + plusBalance;
    }

    private void simulateSellAll(MockAssets mockAssets, Btc5MinuteCandle info) {
        double plusMoney = mockAssets.balance * info.getTradePrice();
        log.info("매도 정보 - 시가: {}, 평단가: {}, 이익: {}", info.getTradePrice(), mockAssets.avgBuyPrice, plusMoney);
        mockAssets.money += plusMoney;
        mockAssets.avgBuyPrice = 0;
        mockAssets.balance = 0;
    }

    private double calculateProfitRatio(MockAssets mockAssets, Btc5MinuteCandle currentChart) {
        return (mockAssets.money + mockAssets.balance * currentChart.getTradePrice() - 1000000) / 1000000 * 100;
    }
}
