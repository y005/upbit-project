package com.example.project.service;

import com.example.project.client.UpbitAssetsClient;
import com.example.project.dto.UpbitAsset;
import com.example.project.entity.Assets;
import com.example.project.entity.Wallet;
import com.example.project.enums.MarketType;
import com.example.project.repository.AssetsRepository;
import com.trader.common.utils.MinuteCandle;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@AllArgsConstructor
public class UpbitAssetsService {

  private final UpbitAssetsClient upbitAssetsClient;
  private final AssetsRepository assetsRepository;
  private final UpbitCrawlerService upbitCrawlerService;
  private final SlackService slackService;

  public void sendWalletInfo() {
    Assets assets = updateWallet();
    slackService.sendAssetsInfo(assets);
    log.info("슬랙 메세지 전송");
  }

  @Transactional
  public Assets updateWallet() {
    Map<String, UpbitAsset> walletInfo = upbitAssetsClient.getUpbitWallet();
    //코인별 개별 수익 및 수익률 계산
    List<Wallet> wallets = walletInfo.values().stream()
        .filter((asset) -> !asset.getCurrency().equals("KRW"))
        .map((asset) -> {
          MarketType type = MarketType.toMarketType("KRW-" + asset.getCurrency());
          MinuteCandle marketInfo = upbitCrawlerService.getRecent5MinCandleInfo(type);
          return Wallet.of(asset, marketInfo);
        }).toList();

    if (wallets.isEmpty()) {
      Assets entity = new Assets(upbitAssetsClient.getMoney(), upbitAssetsClient.getMoney(), 0, 0);
      assetsRepository.save(entity);
      return entity;
    }
    //전체 수익 및 수익률 계산
    double totalTradePrice = wallets.stream().map((e) -> e.getBalance() * e.getTradePrice())
        .reduce(0d, Double::sum);
    double totalProfit = wallets.stream().map(Wallet::getProfit).reduce(0d, Double::sum);
    double totalInvest = wallets.stream().map((e) -> e.getBalance() * e.getAvgBuyPrice())
        .reduce(0d, Double::sum);
    double totalProfitRate = (totalProfit / totalInvest) * 100;
    Assets entity = new Assets(totalTradePrice, totalInvest, totalProfit, totalProfitRate);
    entity.setWallets(wallets);
    assetsRepository.save(entity);
    return entity;
  }
}
