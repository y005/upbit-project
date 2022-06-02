package com.example.project.client;

import com.example.project.config.UpbitConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trader.common.enums.MinuteType;
import com.trader.common.utils.MinuteCandle;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class MyUpbitCandleClient implements UpbitCandleClient {
    private final UpbitConfig upbitConfig;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public MyUpbitCandleClient(UpbitConfig upbitConfig, RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.upbitConfig = upbitConfig;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<MinuteCandle> getMinuteCandle(MinuteType minuteType, MarketType marketType, int count, LocalDateTime localDateTime) {
        String url = makeUrl(minuteType.getMinute(), marketType.getType(), count, localDateTime);
        Object[] objects = request(url);
        return Arrays.stream(objects)
                .map(object -> objectMapper.convertValue(object, MinuteCandle.class))
                .collect(toList());
    }

    private String makeUrl(int unit, String marketType, int count, LocalDateTime time) {
        StringBuilder url = new StringBuilder(upbitConfig.getServerUrl());

        url.append("/v1/candles/minutes/");
        url.append(unit);
        url.append("?market=");
        url.append(marketType);
        url.append("&count=");
        url.append(count);
        url.append("&to=");

        String timeEncode = time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:00"));
        try {
            timeEncode = URLEncoder.encode(timeEncode, "UTF-8");
            url.append(timeEncode);
            return url.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private Object[] request(String url) {
        RequestEntity<Void> request = RequestEntity.get(URI.create(url))
                .accept(MediaType.APPLICATION_JSON)
                .build();

        ResponseEntity<Object[]> response = restTemplate.exchange(request, Object[].class);
        return response.getBody();
    }
}
