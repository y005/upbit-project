package com.example.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpbitAsset {
    @JsonProperty("currency")
    private String currency;

    @JsonProperty("balance")
    private Double balance;

    @JsonProperty("avg_buy_price")
    private Double avgBuyPrice;

    @Override
    public String toString() {
        return "UpbitAsset{" +
                "currency='" + currency + '\'' +
                ", balance=" + balance +
                ", avgBuyPrice=" + avgBuyPrice +
                '}';
    }
}
