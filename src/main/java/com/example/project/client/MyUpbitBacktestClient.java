package com.example.project.client;

import com.example.project.annotation.BacktestErrorHandler;
import com.example.project.config.UpbitConfig;
import com.example.project.dto.UpbitAsset;
import com.example.project.enums.CoinType;
import com.example.project.util.UpbitUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Component
@AllArgsConstructor
public class MyUpbitBacktestClient implements UpbitBacktestClient{
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
    @BacktestErrorHandler
    public double getCoinVolume(CoinType currency) {
        return getUpbitWallet().get(currency.getType()).getBalance();
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
