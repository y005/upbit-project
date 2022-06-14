package com.example.project.client;

import com.example.project.entity.Btc5MinuteCandle;
import com.example.project.enums.TradeType;

import java.util.List;

public interface InvestmentStrategy {
    TradeType makeDecision(List<Btc5MinuteCandle> result, int DROP_CNT, int DROP_RATE);
}
