package com.example.project.enums;

import java.util.Arrays;

public enum TradeType {
    //매수
    BID("bid"),
    //매도
    ASK("ask"),
    HOLD("hold"),
    ;

    private final String type;

    TradeType(String type) {
        this.type = type;
    }

    public static TradeType toTradeType(String type) {
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
