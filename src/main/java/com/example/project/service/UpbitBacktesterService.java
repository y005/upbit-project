package com.example.project.service;

import com.example.project.client.UpbitBacktestClient;
import com.example.project.dto.UpbitAsset;
import com.example.project.entity.Wallet;
import com.example.project.enums.MarketType;
import com.example.project.enums.TradeType;
import com.example.project.repository.WalletRepository;
import com.trader.common.utils.MinuteCandle;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class UpbitBacktesterService {
    private final UpbitBacktestClient upbitBacktestClient;
    private final UpbitCrawlerService upbitCrawlerService;
    private final WalletRepository walletRepository;

    public List<Wallet> updateWallet() {
        Map<String, UpbitAsset> wallet = upbitBacktestClient.getUpbitWallet();
        List<Wallet> result =
                wallet.values().stream()
                        .filter((asset)-> !asset.getCurrency().equals("KRW"))
                        .map((asset) -> {
                            MarketType type = MarketType.toMarketType("KRW-" + asset.getCurrency());
                            MinuteCandle marketInfo = upbitCrawlerService.getCurrent5MinCandleInfo(type);
                            return Wallet.of(asset, marketInfo);
                        }).toList();
        walletRepository.saveAll(result);
        return result;
    }

    public TradeType makeDecision(MarketType marketType) {
        List<Wallet> wallet = updateWallet();
        return null;
    }

    public void algorithm() {

    }
}
