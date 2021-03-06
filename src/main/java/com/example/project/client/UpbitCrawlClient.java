package com.example.project.client;

import com.example.project.enums.MarketType;
import com.trader.common.enums.MinuteType;
import com.trader.common.utils.MinuteCandle;

import java.time.LocalDateTime;
import java.util.List;

public interface UpbitCrawlClient {
    List<MinuteCandle> getMinuteCandle(MinuteType minuteType, MarketType marketType, int count, LocalDateTime localDateTime);
}