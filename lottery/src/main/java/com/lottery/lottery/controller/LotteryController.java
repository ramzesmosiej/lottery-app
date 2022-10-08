package com.lottery.lottery.controller;

import com.lottery.lottery.service.LotteryService;
import com.openfeign.openfeign.clients.NotificationClient;
import com.openfeign.openfeign.clients.ParticipantClient;
import com.shared2.classes.NotificationBody;
import com.shared2.classes.Participant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "/play")
@RestController
public class LotteryController {

    private final LotteryService lotteryService;
    private final ParticipantClient participantClient;
    private static final Logger log = LoggerFactory.getLogger(LotteryController.class);
    private final NotificationClient notificationClient;

    public LotteryController(LotteryService lotteryService, ParticipantClient participantClient, NotificationClient notificationClient) {
        this.lotteryService = lotteryService;
        this.participantClient = participantClient;
        this.notificationClient = notificationClient;
    }

    @PostMapping("/{pesel}")
    public ResponseEntity<?> playLottery(@PathVariable String pesel) {
        if (!participantClient.hasActiveTokens(pesel)) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(
                    "You do not have enough tokens to play"
            );
        }
        participantClient.subtractToken(pesel);
        boolean isWon = lotteryService.isLotteryWon();
        Participant participant = participantClient.getParticipantLottery(pesel).get();
        lotteryService.createLotteryTrial(pesel, isWon);
        log.info("Player with pesel: " + pesel + "is playing lottery with a result" + isWon);

        notificationClient.createNotification(new NotificationBody(pesel, participant.getEmail(), "the result is " + isWon));
        return ResponseEntity.ok("You played. The result will come per Email.");
    }

}

