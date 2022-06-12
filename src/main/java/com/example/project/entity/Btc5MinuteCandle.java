package com.example.project.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "btc_5min_candle")
public class Btc5MinuteCandle {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_time", unique = true)
    private LocalDateTime time;

    @Column(name = "opening_price")
    private double openingPrice;

    @Column(name = "trade_price")
    private double tradePrice;

    @Column(name = "high_price")
    private double highPrice;

    @Column(name = "low_price")
    private double lowPrice;

    @Column(name = "volume")
    private double volume;

    public Btc5MinuteCandle(LocalDateTime time, double openingPrice, double tradePrice, double highPrice, double lowPrice, double volume) {
        this.time = time;
        this.openingPrice = openingPrice;
        this.tradePrice = tradePrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.volume = volume;
    }

    public Btc5MinuteCandle() {
    }

    @Override
    public String toString() {
        return "Btc5MinuteCandle{" +
                "time=" + time +
                ", openingPrice=" + openingPrice +
                ", tradePrice=" + tradePrice +
                ", highPrice=" + highPrice +
                ", lowPrice=" + lowPrice +
                ", volume=" + volume +
                '}';
    }
}
