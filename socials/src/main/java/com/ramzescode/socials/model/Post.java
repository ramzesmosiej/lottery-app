package com.ramzescode.socials.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Post {

    @Id
    @SequenceGenerator(
            name = "post_sequence",
            sequenceName = "post_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "post_sequence")
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;
    @ManyToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            nullable = false
    )
    private User user;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "likes_number", nullable = false)
    private int likesNumber;

    @ManyToMany(mappedBy = "likedPosts")
    private List<User> usersThatLikedThePost = new ArrayList<>();


    public Post() {
    }

    public Post(String title, LocalDateTime createdAt, String content, int likesNumber) {
        this.title = title;
        this.createdAt = createdAt;
        this.content = content;
        this.likesNumber = likesNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public List<User> getUsersThatLikedThePost() {
        return usersThatLikedThePost;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLikesNumber() {
        return likesNumber;
    }

    public void setLikesNumber(int likesNumber) {
        this.likesNumber = likesNumber;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", user=" + user +
                ", title='" + title + '\'' +
                ", createdAt=" + createdAt +
                ", content='" + content + '\'' +
                ", likesNumber=" + likesNumber +
                '}';
    }
}
