package com.example.project.client;

import com.example.project.dto.UpbitAsset;
import com.example.project.entity.Btc5MinuteCandle;
import com.example.project.enums.TradeType;
import com.example.project.repository.Btc5MinuteCandleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class Btc5MinInvestmentStrategy implements InvestmentStrategy {
    private final Btc5MinuteCandleRepository btc5MinuteCandleRepository;
    private final UpbitBacktestClient upbitBacktestClient;

    @Override
    public TradeType makeDecision() {
        int count = 0;
        Btc5MinuteCandle cur, prev = null;
        LocalDateTime before1Hour = LocalDateTime.now().minusHours(1);
        List<Btc5MinuteCandle> result = btc5MinuteCandleRepository.findBtc5MinuteCandlesByTimeGreaterThanOrderByTimeDesc(before1Hour);

        for (int i = 0; (i < result.size()) && (count < 3); i++) {
            cur = result.get(i);
            if (isOkayToContinue(prev, cur)) {
                if (isOkayToCount(prev, cur)) {++count;}
            }
            else {break;}
            prev = cur;
        }
        if (isOkayToAsk(result.get(0))) {return TradeType.ASK;}
        else if (isOkayToBid(count)) {return TradeType.BID;}
        return TradeType.HOLD;
    }

    private boolean isOkayToContinue(Btc5MinuteCandle prev, Btc5MinuteCandle cur) {
        //이전 차트와 현재 차트가 음봉인 경우 1틱 조건 계속 확인 가능
        return ((prev == null) && (checkMinusCandle(cur))) || ((prev != null) && checkMinusCandle(prev) && checkMinusCandle(cur));
    }

    private boolean checkMinusCandle(Btc5MinuteCandle btc5MinuteCandle) {
        return btc5MinuteCandle.getOpeningPrice() > btc5MinuteCandle.getTradePrice();
    }

    private boolean isOkayToCount(Btc5MinuteCandle prev, Btc5MinuteCandle cur) {
        return (prev != null) && (checkBigDrop(prev, cur));
    }

    private boolean checkBigDrop(Btc5MinuteCandle prev, Btc5MinuteCandle cur) {
        return calculateDropRatio(prev, cur) >= 33;
    }

    private double calculateDropRatio(Btc5MinuteCandle prev, Btc5MinuteCandle cur) {
        double priceRange = (prev.getOpeningPrice() - cur.getTradePrice());
        double prevMidPrice = (prev.getOpeningPrice() + prev.getTradePrice()) / 2;
        double curMidPrice = (cur.getOpeningPrice() + cur.getTradePrice()) / 2;
        return (prevMidPrice - curMidPrice) / priceRange * 100;
    }

    private boolean isOkayToAsk(Btc5MinuteCandle cur) {
        //최근 캔들 차트가 양봉이고 코인을 보유하고 있는 경우 전량 매도
        return haveBtc() && !checkMinusCandle(cur);
    }

    private boolean haveBtc() {
        Map<String, UpbitAsset> result = upbitBacktestClient.getUpbitWallet();
        return result.containsKey("BTC");
    }

    private boolean isOkayToBid(int count) {
        return count >= 3;
    }
}
