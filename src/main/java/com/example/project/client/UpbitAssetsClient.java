package com.example.project.client;

import com.example.project.dto.UpbitAsset;
import com.example.project.enums.CoinType;

import java.util.Map;

public interface UpbitAssetsClient {
    Map<String, UpbitAsset> getUpbitWallet();

    double getMoney();

    double getCoinVolume(CoinType coinType);

    double getCoinAvgPrice(CoinType coinType);

    boolean haveBtc();
}
