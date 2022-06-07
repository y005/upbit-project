package com.example.project.client;

import com.example.project.dto.UpbitAsset;

import java.util.Map;

public interface UpbitBacktestClient {
    Map<String, UpbitAsset> getUpbitWallet();
}
