package com.lottery.lottery.repository;

import com.lottery.lottery.model.Lottery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LotteryRepository extends JpaRepository<Lottery, Integer> {
    
}
