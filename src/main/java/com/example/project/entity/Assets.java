package com.example.project.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "assets")
public class Assets {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_trade_price")
    private double totalTradePrice;

    @Column(name = "total_investment")
    private double totalInvestment;

    @Column(name = "total_profit")
    private double totalProfit;

    @Column(name = "total_profit_rate")
    private double totalProfitRate;

    @OneToMany(mappedBy = "assets", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Wallet> wallets = new ArrayList<>();

    @Column(name = "start_time")
    private LocalDateTime time;

    public Assets(double totalTradePrice, double totalInvestment, double totalProfit, double totalProfitRate) {
        this.totalTradePrice = totalTradePrice;
        this.totalInvestment = totalInvestment;
        this.totalProfit = totalProfit;
        this.totalProfitRate = totalProfitRate;
        this.time = LocalDateTime.of(
                LocalDateTime.now().getYear(),
                LocalDateTime.now().getMonth(),
                LocalDateTime.now().getDayOfMonth(),
                LocalDateTime.now().getHour(),
                LocalDateTime.now().getMinute(),
                0);
    }

    public Assets() {

    }

    @Override
    public String toString() {
        String walletString = wallets.size() > 0 ? makeWalletInfo() : "";
        return "전체 평가 금액: " + format(totalTradePrice) +
                "\n전체 투자 금액: " + format(totalInvestment) +
                "\n전체 이익 금액: " + format(totalProfit) +
                "\n전체 이익률: " + format(totalProfitRate) +
                walletString;
    }

    private String makeWalletInfo() {
        String line = wallets.stream().map(Wallet::toString).reduce("", String::concat);
        return "\n보유 코인별 정보:\n" + line;
    }

    public void setWallets(List<Wallet> wallets) {
        this.wallets = wallets;
        this.wallets.forEach(
                (ele) -> {
                    ele.setAssets(this);
                }
        );
    }

    private String format(double value) {
        return String.format("%.2f", value);
    }
}
