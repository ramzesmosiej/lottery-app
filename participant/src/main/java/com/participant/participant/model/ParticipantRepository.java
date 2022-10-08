package com.participant.participant.model;

import com.shared2.classes.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Integer> {
    Optional<Participant> findByPesel(String pesel);
}
