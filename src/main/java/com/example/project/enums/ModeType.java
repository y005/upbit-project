package com.example.project.enums;

import java.util.Arrays;

public enum ModeType {
    TEST("test"),
    MONITORING("monitoring"),
    BACKGROUND("background"),
    BACKTESTER("backtester"),
    ORDERER("orderer"),
    CRAWLER("crawler");

    private final String type;

    ModeType(String type) {
        this.type = type;
    }

    public static ModeType toModeType(String type) {
        return Arrays.stream(values()).filter((e)->e.toString().equals(type)).findFirst().orElseThrow(()-> new RuntimeException("type not found"));
    }

    public String getType() {
        return toString();
    }

    @Override
    public String toString() { return type; }
}
