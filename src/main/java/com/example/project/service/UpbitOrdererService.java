package com.example.project.service;

import com.example.project.client.UpbitOrderService;
import com.example.project.dto.OrderResult;
import com.example.project.enums.MarketType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UpbitOrdererService {
    private final UpbitOrderService upbitOrderService;

    public OrderResult buy(MarketType marketType, double price) {
        return upbitOrderService.bid(marketType, price);
    }

    public OrderResult sell(MarketType marketType, double volume) {
        return upbitOrderService.ask(marketType, volume);
    }
}
