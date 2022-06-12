package com.example.project.application;

import com.example.project.config.ModeConfig;
import com.example.project.enums.MarketType;
import com.example.project.enums.ModeType;
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
    private UpbitBacktesterService upbitBacktesterService;

    @Override
    public void run(ApplicationArguments args) {
        mainLogic();
    }

    @Scheduled(cron = "0 0/5 22-23 * * *")
    private void mainLogic() {
        try {
            ModeType mode = ModeType.toModeType(modeConfig.getMode());
            switch (mode) {
                case BACKGROUND -> {
                    upbitCrawlerService.saveCoin5MinCandleInfo(MarketType.KRW_BTC, 1, LocalDateTime.now());
                    upbitOrdererService.trade(MarketType.KRW_BTC);
                    upbitBacktesterService.sendWalletInfo();
                }
                case MONITORING -> upbitBacktesterService.sendWalletInfo();
            }
        }
        catch (Exception e) {
            log.info("백그라운드 실행 중 에러 발생: {}", e.getMessage());
        }
    }
}
