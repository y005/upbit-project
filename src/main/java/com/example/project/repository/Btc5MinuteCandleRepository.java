package com.example.project.repository;

import com.example.project.entity.Btc5MinuteCandle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Btc5MinuteCandleRepository extends JpaRepository<Btc5MinuteCandle, Long> {
}
