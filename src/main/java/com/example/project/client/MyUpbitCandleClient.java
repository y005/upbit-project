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
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
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
        URI url = makeUrl(minuteType.getMinute(), marketType.getType(), count, localDateTime);
        Object[] objects = request(url);
        return Arrays.stream(objects)
                .map(object -> objectMapper.convertValue(object, MinuteCandle.class))
                .collect(toList());
    }

    private URI makeUrl(int unit, String marketType, int count, LocalDateTime time) {
        return UriComponentsBuilder
                .fromUriString(upbitConfig.getServerUrl())
                .path("/v1/candles/minutes/{unit}")
                .queryParam("market", marketType)
                .queryParam("to", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(time))
                .queryParam("count", count)
                .encode(StandardCharsets.UTF_8)
                .buildAndExpand(unit)
                .toUri();
    }

    private Object[] request(URI url) {
        RequestEntity<Void> request = RequestEntity.get(url)
                .accept(MediaType.APPLICATION_JSON)
                .build();

        ResponseEntity<Object[]> response = restTemplate.exchange(request, Object[].class);
        return response.getBody();
    }
}
