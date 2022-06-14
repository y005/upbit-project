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
            return "백테스팅 결과: " + DROP_RATE +
                    "%의 하락폭이 " + DROP_CNT + "번 발생할 때 매수하는 전략을 선택할 때 과거 한 달 간의 모의 투자 이익률이 " + String.format("%.3f", maxValue) +
                    "% 발생했습니다.";
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
