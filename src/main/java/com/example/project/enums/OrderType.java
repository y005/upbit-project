package com.example.project.enums;

import java.util.Arrays;

public enum OrderType {
  LIMIT("limit"),
  PRICE("price"),
  MARKET("market");

  private final String type;

  OrderType(String type) {
    this.type = type;
  }

  public static OrderType toOrderType(String type) {
    return Arrays.stream(values()).filter((e) -> e.toString().equals(type)).findFirst()
        .orElseThrow(() -> new RuntimeException("type not found"));
  }

  public String getType() {
    return toString();
  }

  @Override
  public String toString() {
    return type;
  }
}
