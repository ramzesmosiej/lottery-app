package com.ramzescode.socials.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FollowingKey implements Serializable {

    @Column(name = "follower_id")
    private Long followerId;

    @Column(name = "following_id")
    private Long followingId;

    public FollowingKey() {
    }

    public FollowingKey(Long followerId, Long followingId) {
        this.followerId = followerId;
        this.followingId = followingId;
    }

    public Long getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Long followerId) {
        this.followerId = followerId;
    }

    public Long getFollowingId() {
        return followingId;
    }

    public void setFollowingId(Long followingId) {
        this.followingId = followingId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FollowingKey that = (FollowingKey) o;
        return Objects.equals(followerId, that.followerId) && Objects.equals(followingId, that.followingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(followerId, followingId);
    }

    @Override
    public String toString() {
        return "FollowingKey{" +
                "followerId=" + followerId +
                ", followingId=" + followingId +
                '}';
    }
}
