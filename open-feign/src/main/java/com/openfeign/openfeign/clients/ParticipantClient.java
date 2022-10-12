package com.openfeign.openfeign.clients;

import DTO.Participant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;
@Component
@FeignClient("participant")
public interface ParticipantClient {
    @GetMapping("participants/active/{pesel}")
    boolean hasActiveTokens(@PathVariable(value = "pesel") String pesel);

    @GetMapping("participants/lottery/{pesel}")
    Optional<Participant> getParticipantLottery(@PathVariable(value = "pesel") String pesel);

    @PostMapping("participants/subtract/{pesel}")
    ResponseEntity<?> subtractToken(@PathVariable(value = "pesel") String pesel);

    @PostMapping("participants/add/{pesel}")
    ResponseEntity<?> addToken(@PathVariable(value = "pesel") String pesel);
}