package com.payment.payment.controller;


import com.openfeign.openfeign.clients.ParticipantClient;
import com.payment.payment.model.Payment;
import com.payment.payment.model.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
@RestController
@RequestMapping("/pay")
public class PaymentController {

    private final PaymentRepository repository;
    private final ParticipantClient participantClient;

    private final Environment environment;

//    @Value("${CONTAINER_NAME}")
    private String name;

    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);

//    private KafkaTemplate<String, String> kafkaTemplate;

    public PaymentController(PaymentRepository repository, ParticipantClient participantClient, Environment environment) {
        this.repository = repository;
        this.participantClient = participantClient;
        this.environment = environment;
    }

    @PatchMapping("/{pesel}")
    public ResponseEntity<?> payForToken(@PathVariable String pesel) throws UnknownHostException {
        repository.saveAndFlush(new Payment(pesel, LocalDateTime.now()));
//        kafkaTemplate.send("payment", "Participant with a pesel: " + pesel + " made a payment." );
        participantClient.addToken(pesel);
//        String body = "handled by" + name;
        log.info("Payment made by player with pesel " + pesel + " handled by a server with host address " + InetAddress.getLocalHost().getHostAddress() + "and port: " + environment.getProperty("server.port"));
        return ResponseEntity.ok().build();
    }
}
