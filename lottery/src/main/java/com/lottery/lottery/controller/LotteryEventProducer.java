package com.lottery.lottery.controller;
import DTO.LotteryEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class LotteryEventProducer {

    private static final Logger logger = LoggerFactory.getLogger(LotteryEventProducer.class);

    private KafkaTemplate<String, LotteryEvent> kafkaTemplate;

    public LotteryEventProducer(KafkaTemplate<String, LotteryEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(LotteryEvent lotteryEvent) {

        logger.info(String.format("Lottery event -> %s", lotteryEvent.toString()));

        Message<LotteryEvent> message = MessageBuilder.withPayload(lotteryEvent).
                setHeader(KafkaHeaders.TOPIC, "lottery")
                .build();

        kafkaTemplate.send(message);
    }
}
