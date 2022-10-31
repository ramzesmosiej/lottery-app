package com.ramzescode.socials.domain;

import javax.persistence.*;

@Entity(name = "FollowingRelationship")

public class FollowingRelationship {

    @EmbeddedId
    private FollowingRelationshipKey id = new FollowingRelationshipKey();

    @ManyToOne
    @MapsId("followerId")
    @JoinColumn(name = "follower_id")
    AppUser follower;

    @ManyToOne
    @MapsId("followingId")
    @JoinColumn(name = "following_id")
    AppUser following;

    public FollowingRelationship() {
    }

    public FollowingRelationship(AppUser follower, AppUser following) {
        this.follower = follower;
        this.following = following;
    }

    public FollowingRelationshipKey getId() {
        return id;
    }

    public void setId(FollowingRelationshipKey id) {
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
