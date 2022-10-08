package com.participant.participant.service;

import com.shared2.classes.Participant;
import com.participant.participant.controller.ParticipantRegistrationRequest;
import com.participant.participant.model.ParticipantRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParticipantService {

    private final ParticipantRepository participantRepository;

    public ParticipantService(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    public Participant registerParticipant(ParticipantRegistrationRequest registrationRequest) {
        Participant participant = new Participant(registrationRequest.getName(), registrationRequest.getSurname(),
                registrationRequest.getPesel(), registrationRequest.getEmail(), 1);
        return participantRepository.saveAndFlush(participant);
    }

    public ResponseEntity<?> getParticipant(String pesel) {
        return participantRepository.findByPesel(pesel).map(ResponseEntity::ok).
                orElseGet(() -> ResponseEntity.notFound().build());
    }

    public Optional<Participant> getOptionalParticipant(String pesel) {
        return participantRepository.findByPesel(pesel);
    }

    public boolean hasActiveTokens(String pesel) {
        return participantRepository.findByPesel(pesel).map(participant -> {
            return participant.getGrantedTokens() > 0;
        }).orElse(false);
    }

    public void saveParticipant(Participant participant) {
        participantRepository.saveAndFlush(participant);
    }

    public List<Participant> getParticipants() {
        return participantRepository.findAll();
    }
}
