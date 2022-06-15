package com.example.project.service;

import com.example.project.client.InvestmentStrategy;
import com.example.project.entity.Btc5MinuteCandle;
import com.example.project.enums.TradeType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class InvestStrategyService {

  private final InvestmentStrategy investmentStrategy;

  public TradeType makeDecision(List<Btc5MinuteCandle> list) {
    return investmentStrategy.makeDecision(list, 1, 2);
  }
}
