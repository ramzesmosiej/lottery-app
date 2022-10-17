package com.ramzescode.socials.model;

import javax.persistence.*;

@Entity
public class Following {

    @EmbeddedId
    private FollowingKey id;

    @ManyToOne
    @MapsId("followerId")
    @JoinColumn(name = "follower_id")
    User follower;

    @ManyToOne
    @MapsId("followingId")
    @JoinColumn(name = "following_id")
    User following;

    public Following() {
    }

    public Following(User follower, User following) {
        this.follower = follower;
        this.following = following;
    }

    public FollowingKey getId() {
        return id;
    }

    public void setId(FollowingKey id) {
        this.id = id;
    }

    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }

    public User getFollowing() {
        return following;
    }

    public void setFollowing(User following) {
        this.following = following;
    }
}
