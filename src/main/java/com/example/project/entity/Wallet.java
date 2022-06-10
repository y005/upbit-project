package com.example.project.entity;

import com.example.project.dto.UpbitAsset;
import com.trader.common.utils.MinuteCandle;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "wallet")
public class Wallet {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "currency")
    private String currency;

    @Column(name = "balance")
    private double balance;

    @Column(name = "avg_buy_price")
    private double avgBuyPrice;

    @Column(name = "trade_price")
    private double tradePrice;

    @Column(name = "profit")
    private double profit;

    @Column(name = "profit_percent")
    private double profitPercent;

    @Column(name = "start_time")
    private LocalDateTime time;

    public Wallet(String currency, double balance, double avgBuyPrice, double tradePrice, double profit, double profitPercent, LocalDateTime time) {
        this.currency = currency;
        this.balance = balance;
        this.avgBuyPrice = avgBuyPrice;
        this.tradePrice = tradePrice;
        this.profit = profit;
        this.profitPercent = profitPercent;
        this.time = LocalDateTime.of(
                LocalDateTime.now().getYear(),
                LocalDateTime.now().getMonth(),
                LocalDateTime.now().getDayOfMonth(),
                LocalDateTime.now().getHour(),
                LocalDateTime.now().getMinute(),
                0);
    }

    public Wallet() {
    }

    public static Wallet of(UpbitAsset upbitAsset, MinuteCandle marketInfo) {
        double invest = getInvest(upbitAsset);
        double profit = getProfit(upbitAsset, marketInfo);
        double profitRate = getProfitRate(upbitAsset, marketInfo);
        return new Wallet(upbitAsset.getCurrency(),
                upbitAsset.getBalance(),
                upbitAsset.getAvgBuyPrice(),
                marketInfo.getTradePrice(),
                profit - invest,
                profitRate,
                LocalDateTime.now());
    }

    public static double getInvest(UpbitAsset upbitAsset) {
        return upbitAsset.getBalance() * upbitAsset.getAvgBuyPrice();
    }

    public static double getProfit(UpbitAsset upbitAsset, MinuteCandle marketInfo) {
        return upbitAsset.getBalance() * marketInfo.getTradePrice();
    }

    public static double getProfitRate(UpbitAsset upbitAsset, MinuteCandle marketInfo) {
        double invest = getInvest(upbitAsset);
        double profit = getProfit(upbitAsset, marketInfo);
        return (profit - invest) / invest * 100;
    }
}
