package com.shared2.classes;


import jakarta.validation.constraints.Email;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;
    private String pesel;
    @Email
    private String email;
    private int grantedTokens;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "participant")
//    private Set<Lottery> lotteries = new HashSet<>();

    public Participant(String name, String surname, String pesel, String email, int tokens) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.pesel = pesel;
        this.grantedTokens = tokens;
    }

    public Participant() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGrantedTokens() {
        return grantedTokens;
    }

    public void setGrantedTokens(int grantedTokens) {
        this.grantedTokens = grantedTokens;
    }

//    public Set<Lottery> getLotteries() {
//        return lotteries;
//    }
}
