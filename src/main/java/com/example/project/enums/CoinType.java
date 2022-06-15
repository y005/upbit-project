package com.example.project.enums;

import java.util.Arrays;

public enum CoinType {
  KRW("KRW"),
  BTC("BTC");

  private final String type;

  CoinType(String type) {
    this.type = type;
  }

  public static CoinType toCoinType(String type) {
    return Arrays.stream(values()).filter((e) -> e.toString().equals(type)).findFirst()
        .orElseThrow(() -> new RuntimeException("type not found"));
  }

  public static CoinType toCoinType(MarketType type) {
    return Arrays.stream(values())
        .filter((e) -> e.toString().equals(getCoinTypeFromMarketType(type))).findFirst()
        .orElseThrow(() -> new RuntimeException("type not found"));
  }

  public static String getCoinTypeFromMarketType(MarketType type) {
    String line = type.getType();
    return line.substring(line.lastIndexOf("-") + 1);
  }

  public String getType() {
    return toString();
  }

  @Override
  public String toString() {
    return type;
  }
}
