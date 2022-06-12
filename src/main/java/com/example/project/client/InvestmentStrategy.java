package com.example.project.client;

import com.example.project.enums.TradeType;

public interface InvestmentStrategy {
    TradeType makeDecision();
}
