package com.lottery.lottery.model;


import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@RequiredArgsConstructor
public class Lottery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String pesel;
    private boolean result;
    private LocalDateTime playedAt;

    public Lottery(String pesel, boolean isWon, LocalDateTime now) {
        this.pesel = pesel;
        this.result = isWon;
        playedAt = now;
    }
}
