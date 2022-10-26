package com.ramzescode.socials.repository;

import com.ramzescode.socials.domain.FollowingRelationship;
import com.ramzescode.socials.domain.FollowingRelationshipKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowingRepository extends JpaRepository<FollowingRelationship, FollowingRelationshipKey> {
}
