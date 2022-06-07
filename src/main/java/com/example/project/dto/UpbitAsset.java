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

    @JsonProperty("locked")
    private Double locked;

    @JsonProperty("avg_buy_price")
    private Double avgBuyPrice;

    @JsonProperty("avg_buy_price_modified")
    private Boolean avgBuyPriceModified;

    @Override
    public String toString() {
        return "UpbitAsset{" +
                "currency='" + currency + '\'' +
                ", balance=" + balance +
                ", locked=" + locked +
                ", avgBuyPrice=" + avgBuyPrice +
                ", avgBuyPriceModified=" + avgBuyPriceModified +
                '}';
    }
}
