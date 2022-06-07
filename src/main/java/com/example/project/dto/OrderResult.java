package com.example.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderResult {
    @JsonProperty("uuid")
    private UUID uuid;

    @JsonProperty("side")
    private String tradeType;

    @JsonProperty("ord_type")
    private String orderType;

    @JsonProperty("price")
    private Double price;

    @JsonProperty("avg_price")
    private Double avgPrice;

    @JsonProperty("state")
    private String state;

    @JsonProperty("market")
    private String market;

    @JsonProperty("volume")
    private Double volume;

    @JsonProperty("remaining_volume")
    private Double remainingVolume;

    @JsonProperty("reserved_fee")
    private Double reservedFee;

    @JsonProperty("remaining_fee")
    private Double remainingFee;

    @JsonProperty("paid_fee")
    private Double paidFee;

    @JsonProperty("locked")
    private Double locked;

    @JsonProperty("executed_volume")
    private Double executed_volume;

    @Override
    public String toString() {
        return "OrderResult{" +
                "uuid=" + uuid +
                ", tradeType='" + tradeType + '\'' +
                ", orderType='" + orderType + '\'' +
                ", price=" + price +
                ", avgPrice=" + avgPrice +
                ", state='" + state + '\'' +
                ", market='" + market + '\'' +
                ", volume=" + volume +
                ", remainingVolume=" + remainingVolume +
                ", reservedFee=" + reservedFee +
                ", remainingFee=" + remainingFee +
                ", paidFee=" + paidFee +
                ", locked=" + locked +
                ", executed_volume=" + executed_volume +
                '}';
    }
}