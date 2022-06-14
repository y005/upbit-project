package com.example.project.client;

import com.example.project.annotation.AssetsErrorHandler;
import com.example.project.config.UpbitConfig;
import com.example.project.dto.UpbitAsset;
import com.example.project.enums.CoinType;
import com.example.project.util.UpbitUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class MyUpbitAssetsClient implements UpbitAssetsClient {
    private final UpbitUtil upbitUtil;
    private final UpbitConfig upbitConfig;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public Map<String, UpbitAsset> getUpbitWallet() {
        URI url = makeWalletUrl();
        String token = upbitUtil.makeToken();
        Object[] objects = request(url, token);

        return Arrays.stream(objects)
                .map(object -> objectMapper.convertValue(object, UpbitAsset.class))
                .collect(Collectors.toMap(UpbitAsset::getCurrency, Function.identity()));
    }

    @Override
    public double getMoney() {
        return getUpbitWallet().get("KRW").getBalance();
    }

    @Override
    @AssetsErrorHandler
    public double getCoinVolume(CoinType currency) {
        return getUpbitWallet().get(currency.getType()).getBalance();
    }

    @Override
    @AssetsErrorHandler
    public double getCoinAvgPrice(CoinType currency) {
        return getUpbitWallet().get(currency.getType()).getAvgBuyPrice();
    }

    @Override
    public boolean haveBtc() {
        Map<String, UpbitAsset> result = getUpbitWallet();
        return result.containsKey("BTC");
    }

    private URI makeWalletUrl() {
        return UriComponentsBuilder
                .fromUriString(upbitConfig.getServerUrl())
                .path("/v1/accounts")
                .encode(StandardCharsets.UTF_8)
                .buildAndExpand()
                .toUri();
    }

    private Object[] request(URI url, String authenticationToken) {
        RequestEntity<Void> request = RequestEntity.get(url)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", authenticationToken)
                .build();

        ResponseEntity<Object[]> response = restTemplate.exchange(request, Object[].class);
        return response.getBody();
    }
}
