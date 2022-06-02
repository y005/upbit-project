package com.example.project;

import com.trader.common.enums.MarketFlowType;
import com.trader.common.enums.MinuteType;
import com.trader.common.utils.MinuteCandle;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public interface UpbitCandleClient {

    List<MinuteCandle> getMinuteCandle(MinuteType minuteType, MarketFlowType marketFlowType, int count, LocalDateTime localDateTime);
}
