package com.ramzescode.socials.service;

import com.ramzescode.socials.domain.Post;
import com.ramzescode.socials.repository.PostRepository;
import com.ramzescode.socials.rest.errors.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post updatePost(Post post) {
        if (post.getId() == null) {
            throw new BadRequestException("ID cannot be found");
        }
        Post existingPost = postRepository.findPostByIdElseThrow(post.getId());
        existingPost.setContent(post.getContent());
        existingPost.setTitle(post.getTitle());
        return postRepository.save(existingPost);
    }
}
