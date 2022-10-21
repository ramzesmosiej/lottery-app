package com.ramzescode.socials.model;

import javax.persistence.*;

@Entity
public class Following {

    @EmbeddedId
    private FollowingKey id;

    @ManyToOne
    @MapsId("followerId")
    @JoinColumn(name = "follower_id")
    AppUser follower;

    @ManyToOne
    @MapsId("followingId")
    @JoinColumn(name = "following_id")
    AppUser following;

    public Following() {
    }

    public Following(AppUser follower, AppUser following) {
        this.follower = follower;
        this.following = following;
    }

    public FollowingKey getId() {
        return id;
    }

    public void setId(FollowingKey id) {
        this.id = id;
    }

    public AppUser getFollower() {
        return follower;
    }

    public void setFollower(AppUser follower) {
        this.follower = follower;
    }

    public AppUser getFollowing() {
        return following;
    }

    public void setFollowing(AppUser following) {
        this.following = following;
    }
}
