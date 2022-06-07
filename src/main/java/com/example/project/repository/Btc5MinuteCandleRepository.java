package com.example.project.repository;

import com.example.project.entity.Btc5MinuteCandle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Btc5MinuteCandleRepository extends JpaRepository<Btc5MinuteCandle, Long> {
}

