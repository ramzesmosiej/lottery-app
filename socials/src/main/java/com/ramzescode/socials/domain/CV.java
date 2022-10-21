package com.ramzescode.socials.model;

import javax.persistence.*;

@Entity(name = "CV")
@Table(name = "CV")
public class CV {
    @Id
    @SequenceGenerator(
            name = "CV_sequence",
            sequenceName = "CV_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "CV_sequence")
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            nullable = false
    )
    private AppUser appUser;
    @Column(name = "age", nullable = false)
    private int age;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "bio", nullable = false)
    private String bio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppUser getUser() {
        return appUser;
    }

    public void setUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public CV() {
    }

    public CV(AppUser appUser, int age, String email, String bio) {
        this.appUser = appUser;
        this.age = age;
        this.email = email;
        this.bio = bio;
    }

    @Override
    public String toString() {
        return "CV{" +
                "id=" + id +
                ", user=" + appUser +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", bio='" + bio + '\'' +
                '}';
    }
}
