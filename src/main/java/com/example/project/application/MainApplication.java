package com.example.project.application;

import com.example.project.annotation.ErrorHandler;
import com.example.project.client.UpbitBacktestClient;
import com.example.project.config.ModeConfig;
import com.example.project.enums.MarketType;
import com.example.project.enums.ModeType;
import com.example.project.service.UpbitAssetsService;
import com.example.project.service.UpbitBacktesterService;
import com.example.project.service.UpbitCrawlerService;
import com.example.project.service.UpbitOrdererService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@AllArgsConstructor
@EnableScheduling
public class MainApplication implements ApplicationRunner {
    private ModeConfig modeConfig;
    private UpbitCrawlerService upbitCrawlerService;
    private UpbitOrdererService upbitOrdererService;
    private UpbitAssetsService upbitAssetsService;
    private UpbitBacktesterService upbitBacktesterService;

    @Override
    public void run(ApplicationArguments args) {
        mainLogic();
    }

    @ErrorHandler
    @Scheduled(cron = "0 0/5 * * * *")
    public void mainLogic() {
        ModeType mode = ModeType.toModeType(modeConfig.getMode());
        switch (mode) {
            case BACKGROUND -> {
                upbitCrawlerService.saveCoin5MinCandleInfoBefore1Hour(MarketType.KRW_BTC, LocalDateTime.now());
                upbitOrdererService.trade(MarketType.KRW_BTC);
                upbitAssetsService.sendWalletInfo();
            }
            case MONITORING -> {
                upbitAssetsService.sendWalletInfo();
            }
            case BACKTESTER -> {
                var result = upbitBacktesterService.backtesting();
                log.info("백테스팅 결과 - {}", result.toString());
            }
            default -> {}
        }
    }
}
