package com.ramzescode.socials.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "User")
@Table(name = "user_account")
public class User {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence")
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;
    @Column(name = "name", nullable = false, columnDefinition = "TEXT")
    private String name;
    @Column(name = "surname", nullable = false, columnDefinition = "TEXT")
    private String surname;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "password", nullable = false, columnDefinition = "TEXT")
    private String password;
    @OneToOne(mappedBy = "user", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private CV cv;
    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private final List<Post> posts = new ArrayList<>();
    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    @JoinTable(
            name = "post_likes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    Set<Post> likedPosts = new HashSet<>();

    public User(String name, String surname, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email = email;
    }

    public User() {
    }

    public void addPost(Post post) {
        if (!posts.contains(post)) {
            posts.add(post);
            post.setUser(this);
        }
    }

    public void likePost(Post post) {
        post.getUsersThatLikedThePost().add(this);
        likedPosts.add(post);
    }

    public void removePost(Post post) {
        if (this.posts.contains(post)) {
            this.posts.remove(post);
            post.setUser(null);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCv(CV cv) {
        this.cv = cv;
    }
}
