package com.participant.participant.controller;

import com.openfeign.openfeign.clients.AuthenticationClient;
import com.participant.participant.service.ParticipantService;
import com.shared2.classes.Participant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/participants")
public class ParticipantController {
    private final ParticipantService participantService;
    private final AuthenticationClient authenticationClient;

    public ParticipantController(ParticipantService customerService, ParticipantService participantService, AuthenticationClient authenticationClient) {
        this.participantService = participantService;
        this.authenticationClient = authenticationClient;
    }

    @PostMapping
    public ResponseEntity<?> registerParticipant(@Valid @RequestBody ParticipantRegistrationRequest registrationRequest) {
        if (authenticationClient.isAuthenticated(registrationRequest.getPesel())) {
            Participant participant = participantService.registerParticipant(registrationRequest);
            return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentContextPath().path(
                    "/{pesel}"
            ).buildAndExpand(registrationRequest.getPesel()).toUri()).body(participant);
        }
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }

    @GetMapping("/active/{pesel}")
    public boolean hasActiveTokens(@PathVariable String pesel) {
        return participantService.hasActiveTokens(pesel);
    }

    @PostMapping("/subtract/{pesel}")
    public ResponseEntity<?> subtractToken(@PathVariable String pesel) {
        return participantService.getOptionalParticipant(pesel).map(
                participant -> {
                    participant.setGrantedTokens(participant.getGrantedTokens() - 1);
                    participantService.saveParticipant(participant);
                    return ResponseEntity.ok().build();
                }
        ).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add/{pesel}")
    public ResponseEntity<?> addToken(@PathVariable String pesel) {
        return participantService.getOptionalParticipant(pesel).map(
                participant -> {
                    participant.setGrantedTokens(participant.getGrantedTokens() + 1);
                    participantService.saveParticipant(participant);
                    return ResponseEntity.ok().build();
                }
        ).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{pesel}")
    public ResponseEntity<?> getParticipant(@PathVariable String pesel) {
        return participantService.getParticipant(pesel);
    }

    @GetMapping("/lottery/{pesel}")
    public Optional<Participant> getParticipantLottery(@PathVariable String pesel) {
        return participantService.getOptionalParticipant(pesel);
    }

    @GetMapping
    public List<Participant> getParticipants() {
        return participantService.getParticipants();
    }
}
