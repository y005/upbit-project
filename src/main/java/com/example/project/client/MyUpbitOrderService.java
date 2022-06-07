package com.example.project.client;

import com.example.project.config.UpbitConfig;
import com.example.project.dto.OrderResult;
import com.example.project.enums.MarketType;
import com.example.project.enums.OrderType;
import com.example.project.enums.TradeType;
import com.example.project.util.UpbitUtil;
import com.google.gson.Gson;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URI;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class MyUpbitOrderService implements UpbitOrderService{
    private final UpbitUtil upbitUtil;
    private final UpbitConfig upbitConfig;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    //시장가 매도 ask - orde_type은 market이고 price이 null 혹은 제외
    @Override
    public OrderResult ask(MarketType marketType, double volume) {
        MultiValueMap<String, String> params = getParam(marketType, TradeType.ASK, OrderType.MARKET);
        params.add("volume", Double.toString(volume));
        return order(params);
    }

    //시장가 매수 bid - orde_type은 price이고 volume이 null 혹은 제외
    @Override
    public OrderResult bid(MarketType marketType, double price) {
        MultiValueMap<String, String> params = getParam(marketType, TradeType.BID, OrderType.PRICE);
        params.add("price", Double.toString(price));
        return order(params);
    }

    private OrderResult order(MultiValueMap<String, String> params) {
        try {
            String queryHash = makeQueryHash(params.toSingleValueMap());
            String token = upbitUtil.makeToken(queryHash);
            String object = request(params, token);
            //URI url = makeOrderUrl();
            //Object object = request(url, params, token);
            OrderResult result = objectMapper.readValue(object, OrderResult.class);
            return result;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private MultiValueMap<String, String> getParam(MarketType marketType, TradeType tradeType, OrderType orderType) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("market", marketType.getType());
        params.add("side", tradeType.getType());
        params.add("ord_type", orderType.getType());
        return params;
    }

    private String makeQueryHash(Map<String, String> params) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        List<String> queryElements = new ArrayList<>();
        for (Map.Entry<String, String> entity : params.entrySet()) {
            queryElements.add(entity.getKey() + "=" + entity.getValue());
        }
        String queryString = String.join("&", queryElements.toArray(new String[0]));
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(queryString.getBytes("UTF-8"));
        String queryHash = String.format("%0128x", new BigInteger(1, md.digest()));
        return queryHash;
    }

    private String request(MultiValueMap<String, String> params, String authenticationToken) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(upbitConfig.getServerUrl() + "/v1/orders");
        request.setHeader("Content-Type", "application/json");
        request.addHeader("Authorization", authenticationToken);
        request.setEntity(new StringEntity(new Gson().toJson(params.toSingleValueMap())));
        HttpResponse response = client.execute(request);
        return EntityUtils.toString(response.getEntity(), "UTF-8");
    }

//    private URI makeOrderUrl() {
//        return UriComponentsBuilder
//                .fromUriString(upbitConfig.getServerUrl())
//                .path("/v1/orders")
//                .encode(StandardCharsets.UTF_8)
//                .buildAndExpand()
//                .toUri();
//    }
//
//    private Object request(URI url, MultiValueMap<String, String> params, String authenticationToken) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setBearerAuth(authenticationToken);
//        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
//        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, request, Object.class);
//        return response.getBody();
//    }
}

