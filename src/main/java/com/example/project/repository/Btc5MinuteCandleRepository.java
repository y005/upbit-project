package com.example.project.repository;

import com.example.project.entity.Btc5MinuteCandle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface Btc5MinuteCandleRepository extends JpaRepository<Btc5MinuteCandle, Long> {
    List<Btc5MinuteCandle> findBtc5MinuteCandlesByTimeGreaterThanOrderByTimeDesc(LocalDateTime time);
    List<Btc5MinuteCandle> findBtc5MinuteCandlesByTimeBetweenOrderByTimeDesc(LocalDateTime after, LocalDateTime before);
}

