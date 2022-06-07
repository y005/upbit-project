package com.example.project.client;

import com.example.project.dto.OrderResult;
import com.example.project.enums.MarketType;

public interface UpbitOrderService {
    public OrderResult bid(MarketType marketType, double price);
    public OrderResult ask(MarketType marketType, double volume);
}
