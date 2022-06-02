package com.example.project.client;

import com.trader.common.enums.MinuteType;
import com.trader.common.utils.MinuteCandle;

import java.time.LocalDateTime;
import java.util.List;

public interface UpbitCandleClient {
    List<MinuteCandle> getMinuteCandle(MinuteType minuteType, MarketType marketType, int count, LocalDateTime localDateTime);
}