package com.ramzescode.socials.repository;

import com.ramzescode.socials.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    default Post findPostByIdElseThrow(Long postId) {
        return findById(postId).orElseThrow(() -> new EntityNotFoundException("Post with id: " + postId + " not found"));
    }
}
