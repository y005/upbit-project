package com.example.project.client;

import com.example.project.entity.Btc5MinuteCandle;
import com.example.project.enums.TradeType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class Btc5MinInvestmentStrategy implements InvestmentStrategy {
    @Override
    public TradeType makeDecision(List<Btc5MinuteCandle> result, int DROP_CNT, int DROP_RATE) {
            int count = 0;
            Btc5MinuteCandle cur, prev = null;

            for (int i = 0; (i < result.size()) && (count < 3); i++) {
                cur = result.get(i);
                if (isOkayToContinue(prev, cur)) {
                    if (isOkayToCount(prev, cur, DROP_RATE)) {++count;}
                }
                else {break;}
                prev = cur;
            }
            if (isOkayToAsk(result.get(0))) {return TradeType.ASK;}
            else if (isOkayToBid(count, DROP_CNT)) {return TradeType.BID;}
        return TradeType.HOLD;
    }

    private boolean isOkayToContinue(Btc5MinuteCandle prev, Btc5MinuteCandle cur) {
        //이전 차트와 현재 차트가 음봉인 경우 1틱 조건 계속 확인 가능
        return ((prev == null) && (checkMinusCandle(cur))) || ((prev != null) && checkMinusCandle(prev) && checkMinusCandle(cur));
    }

    private boolean checkMinusCandle(Btc5MinuteCandle btc5MinuteCandle) {
        return btc5MinuteCandle.getOpeningPrice() > btc5MinuteCandle.getTradePrice();
    }

    private boolean isOkayToCount(Btc5MinuteCandle prev, Btc5MinuteCandle cur, int DROP_RATE) {
        return (prev != null) && (checkBigDrop(prev, cur, DROP_RATE));
    }

    private boolean checkBigDrop(Btc5MinuteCandle prev, Btc5MinuteCandle cur, int DROP_RATE) {
        return calculateDropRatio(prev, cur) >= DROP_RATE;
    }

    private double calculateDropRatio(Btc5MinuteCandle prev, Btc5MinuteCandle cur) {
        double priceRange = (prev.getOpeningPrice() - cur.getTradePrice());
        double prevMidPrice = (prev.getOpeningPrice() + prev.getTradePrice()) / 2;
        double curMidPrice = (cur.getOpeningPrice() + cur.getTradePrice()) / 2;
        return (prevMidPrice - curMidPrice) / priceRange * 100;
    }

    private boolean isOkayToAsk(Btc5MinuteCandle cur) {
        return !checkMinusCandle(cur);
    }

    private boolean isOkayToBid(int count, int DROP_CNT) {
        return count >= DROP_CNT;
    }
}
