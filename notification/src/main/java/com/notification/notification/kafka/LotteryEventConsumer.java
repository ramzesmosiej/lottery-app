package com.notification.notification.kafka;
import DTO.LotteryEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class LotteryEventConsumer {

    private static final Logger logger = LoggerFactory.getLogger(LotteryEventConsumer.class);

    @KafkaListener(topics = "lottery", groupId = "notification")
    public void consume(LotteryEvent lotteryEvent) {
        logger.info(String.format("Lottery event received in notification module -> %s", lotteryEvent.toString()));
        System.out.println("Sending email to the user with pesel" + lotteryEvent.getPesel() + "that his result is " + lotteryEvent.isResult());
    }
}
