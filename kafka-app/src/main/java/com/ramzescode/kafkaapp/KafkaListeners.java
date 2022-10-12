package com.ramzescode.kafkaapp;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    @KafkaListener(topics = "topicTest", groupId = "groupId", containerFactory = "factory")
    void listener(Message data) {
        System.out.print("Listener received: " + data + "\n");
    }

}
