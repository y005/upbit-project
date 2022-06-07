package com.example.project.service;

import com.example.project.enums.MarketType;
import com.example.project.client.UpbitBacktestClient;
import com.example.project.client.UpbitCandleClient;
import com.example.project.dto.UpbitAsset;
import com.example.project.enums.TradeType;
import com.example.project.repository.Btc5MinuteCandleRepository;
import com.trader.common.enums.MinuteType;
import com.trader.common.utils.MinuteCandle;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class UpbitBacktesterService {
    private final UpbitBacktestClient upbitBacktestClient;
    private final UpbitCandleClient upbitCandleClient;
    private final Btc5MinuteCandleRepository btc5MinuteCandleRepository;

    public TradeType makeDecision(MarketType marketType) {
        double profit = getProfitPercent(marketType);

        if (profit >= 30) {
            return TradeType.BID;
        }
        else if ((profit < 30) && (profit > -10)) {
            return TradeType.ASK;
        }
        else {
            return TradeType.HOLD;
        }
    }

    private double getProfitPercent(MarketType marketType) {
        Map<String, UpbitAsset> wallet = upbitBacktestClient.getUpbitWallet();

        if (wallet.containsKey(marketType.getType())) {
            List<MinuteCandle> minuteCandleList = getCoinInfo(marketType);
            double tradePrice = minuteCandleList.get(0).getTradePrice();
            double myPrice = wallet.get(marketType.getType()).getAvgBuyPrice();
            //코인별 수익률 = (코인의 시장가 - 코인의 평단가) / 코인의 현재 가치 * 100
            return (tradePrice - myPrice) / myPrice * 100;
        }
        else {
             throw new RuntimeException("Not a coin in possession - " + marketType.getType());
        }
    }

    private double getProfitPercent() {
        Map<String, UpbitAsset> wallet = upbitBacktestClient.getUpbitWallet();

        //투자 대비 수익률 = (수익금 - 투자금) / 투자금 * 100
        double profit = wallet.values().stream().map(
                (coin)->{
                    List<MinuteCandle> minuteCandleList = getCoinInfo(MarketType.toMarketType(coin.getCurrency()));
                    double tradePrice = minuteCandleList.get(0).getTradePrice();
                    return tradePrice * coin.getBalance();
                }
        ).reduce(0d, (n1, n2) -> n1 + n2);

        double cost =  wallet.values().stream().map(
                (coin)->{
                    double myPrice = coin.getAvgBuyPrice();
                    return myPrice * coin.getBalance();
                }
        ).reduce(0d, (n1, n2) -> n1 + n2);

        return (profit - cost) / cost * 100;
    }

    private double getProfit() {
        Map<String, UpbitAsset> wallet = upbitBacktestClient.getUpbitWallet();

        //전체 수익금 = ((코인의 시장가 - 코인의 평단가) * 보유 수량)의 합
        return wallet.values().stream().map(
                (coin)->{
                    List<MinuteCandle> minuteCandleList = getCoinInfo(MarketType.toMarketType(coin.getCurrency()));
                    double tradePrice = minuteCandleList.get(0).getTradePrice();
                    double myPrice = coin.getAvgBuyPrice();
                    return (tradePrice - myPrice) * coin.getBalance();
                }
        ).reduce(0d, (n1, n2) -> n1 + n2);
    }

    private List<MinuteCandle> getCoinInfo(MarketType marketType) {
        LocalDateTime now = LocalDateTime.now();

        int year = now.getYear();
        int month = now.getDayOfMonth();
        int day = now.getDayOfMonth();
        int hour = now.getHour();
        int min = now.getMinute();
        return upbitCandleClient.getMinuteCandle(MinuteType.FIVE, marketType, 1, LocalDateTime.of(year, month + 1, day, hour, min, 0));
    }
}
