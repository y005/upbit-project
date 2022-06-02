package com.example.project.client;

import java.util.Arrays;

public enum MarketType {
    KRW_BTC("KRW-BTC");

    private final String type;

    MarketType(String type) {
        this.type = type;
    }

    public static MarketType toMarketType(String type) {
        return Arrays.stream(values()).filter((e)->e.toString().equals(type)).findFirst().orElseThrow(()-> new RuntimeException("type not found"));
    }

    public String getType() {
        return toString();
    }

    @Override
    public String toString() {
        return type;
    }
}
