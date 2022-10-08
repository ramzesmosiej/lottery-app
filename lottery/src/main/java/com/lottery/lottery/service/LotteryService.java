package com.lottery.lottery.service;

import com.lottery.lottery.model.Lottery;
import com.lottery.lottery.repository.LotteryRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class LotteryService {

    private final LotteryRepository lotteryRepository;

    public LotteryService(LotteryRepository lotteryRepository) {
        this.lotteryRepository = lotteryRepository;
    }
    public boolean isLotteryWon() {
        return new Random().nextBoolean();
    }
    public void createLotteryTrial(String pesel, boolean isWon) {
        lotteryRepository.save(
                new Lottery(pesel, isWon, LocalDateTime.now())
        );
    }
}
