package com.example.project.service;

import com.example.project.client.UpbitBacktestClient;
import com.example.project.enums.MarketType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@AllArgsConstructor
public class UpbitBacktesterService {
    private final UpbitBacktestClient upbitBacktestClient;
    private final UpbitCrawlerService upbitCrawlerService;
    private final SlackService slackService;

    public static class HyperParams {
        public int DROP_CNT;
        public int DROP_RATE;
        public double maxValue;

        public HyperParams(int DROP_CNT, int DROP_RATE, double maxValue) {
            this.DROP_CNT = DROP_CNT;
            this.DROP_RATE = DROP_RATE;
            this.maxValue = 0d;
        }

        @Override
        public String toString() {
            return "HyperParams{" +
                    "DROP_CNT=" + DROP_CNT +
                    ", DROP_RATE=" + DROP_RATE +
                    ", maxValue=" + maxValue +
                    '}';
        }
    }

    public HyperParams backtesting() {
        upbitCrawlerService.saveCoin5MinCandleInfoBefore1Month(MarketType.KRW_BTC);
        HyperParams hyperParams = findBestHyperParams();
        slackService.sendBacktestInfo(hyperParams);
        return hyperParams;
    }

    private HyperParams findBestHyperParams() {
        HyperParams hyperParams = new HyperParams(0,0, 0);
        double value;

        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 5; j++) {
                value = upbitBacktestClient.testInvestmentStrategy(i, j);
                if (value > hyperParams.maxValue) {
                    hyperParams.DROP_RATE = i;
                    hyperParams.DROP_CNT = j;
                    hyperParams.maxValue = value;
                }
            }
        }
        return hyperParams;
    }
}
