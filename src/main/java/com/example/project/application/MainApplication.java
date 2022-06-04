package com.example.project.application;

import com.example.project.config.ModeConfig;
import com.example.project.config.ModeType;
import com.example.project.service.UpbitBacktesterService;
import com.example.project.service.UpbitCrawlerService;
import com.example.project.service.UpbitOrdererService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MainApplication implements ApplicationRunner {
    private ModeConfig modeConfig;
    private UpbitCrawlerService upbitCrawlerService;
    private UpbitOrdererService upbitOrdererService;
    private UpbitBacktesterService upbitBacktesterService;
    private ApplicationTerminator applicationTerminator;

    public MainApplication(ModeConfig modeConfig, UpbitCrawlerService upbitCrawlerService, UpbitOrdererService upbitOrdererService, UpbitBacktesterService upbitBacktesterService, ApplicationTerminator applicationTerminator) {
        this.modeConfig = modeConfig;
        this.upbitCrawlerService = upbitCrawlerService;
        this.upbitOrdererService = upbitOrdererService;
        this.upbitBacktesterService = upbitBacktesterService;
        this.applicationTerminator = applicationTerminator;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            ModeType mode = ModeType.toModeType(modeConfig.getMode());
            switch (mode) {
                case ORDERER -> System.out.println("do order");
                case CRAWLER -> upbitCrawlerService.saveBtcInfo(LocalDateTime.now());
                case BACKTESTER -> System.out.println("do backtester");
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            applicationTerminator.exit();
        }
    }
}
