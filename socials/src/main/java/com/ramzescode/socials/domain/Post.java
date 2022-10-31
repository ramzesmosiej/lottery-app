package com.ramzescode.socials.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
    private AppUser appUser;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "likes_number", nullable = false)
    private int likesNumber = 0;

    @ManyToMany(mappedBy = "likedPosts")
    private final Set<AppUser> usersThatLikedThePost = new HashSet<>();

    public void deleteLikeByUsername(String username) {
        usersThatLikedThePost.removeIf(post -> post.getUsername().equals(username));
        likesNumber = usersThatLikedThePost.size();
    }


    public Post() {
    }

    public Post(String title, LocalDateTime createdAt, String content) {
        this.title = title;
        this.createdAt = createdAt;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppUser getUser() {
        return appUser;
    }

    public Set<AppUser> getUsersThatLikedThePost() {
        return usersThatLikedThePost;
    }

    public void setUser(AppUser appUser) {
        this.appUser = appUser;
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

    public void addLike() {
        likesNumber += 1;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", user=" + appUser +
                ", title='" + title + '\'' +
                ", createdAt=" + createdAt +
                ", content='" + content + '\'' +
                ", likesNumber=" + likesNumber +
                '}';
    }
}
