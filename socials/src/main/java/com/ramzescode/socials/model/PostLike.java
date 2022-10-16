package com.ramzescode.socials.model;

import javax.persistence.*;

//@Entity(name = "post_like")
//@Table(name = "post_like")
public class PostLike {
    @EmbeddedId
    private PostLikesId id;

    @ManyToOne
    @MapsId("postId")
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @MapsId("postId")
    @JoinColumn(name = "user_id")
    private User user;

    public PostLike(Post post, User user) {
        this.post = post;
        this.user = user;
    }

    public PostLike() {
    }

    public PostLikesId getId() {
        return id;
    }

    public void setId(PostLikesId id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
