package com.example.project.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "BTC")
public class Btc5MinuteCandle {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_time")
    private LocalDateTime time;

    @Column(name = "opening_price")
    private double openingPrice;

    @Column(name = "high_price")
    private double highPrice;

    @Column(name = "low_price")
    private double lowPrice;

    @Column(name = "volume")
    private double volume;
}
