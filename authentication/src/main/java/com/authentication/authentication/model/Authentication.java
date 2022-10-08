package com.authentication.authentication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Past;
import java.time.LocalDateTime;

@Entity
@Data
@RequiredArgsConstructor
public class Authentication {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    private String pesel;
    @Past
    private LocalDateTime interviewedAt;
    private boolean passed;

    public Authentication(String pesel, LocalDateTime now, boolean passed) {
        this.pesel = pesel;
        this.interviewedAt = now;
        this.passed = passed;
    }
}
