package com.ramzescode.socials.rest;

import com.ramzescode.socials.DTO.PostCreationRequest;
import com.ramzescode.socials.domain.AppUser;
import com.ramzescode.socials.domain.Post;
import com.ramzescode.socials.repository.PostRepository;
import com.ramzescode.socials.service.ResponseService;
import com.ramzescode.socials.service.SingleUserNotificationService;
import com.ramzescode.socials.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final UserService userService;

    private final PostRepository postRepository;

    private final SingleUserNotificationService singleUserNotificationService;

    public PostController(UserService userService, PostRepository postRepository, SingleUserNotificationService singleUserNotificationService) {
        this.userService = userService;
        this.postRepository = postRepository;
        this.singleUserNotificationService = singleUserNotificationService;
    }

    @PostMapping
    public ResponseEntity<ResponseService> addPost(@RequestBody PostCreationRequest postCreationRequest) {
        AppUser loggedUser = userService.findLoggedUser();
        Post postToCreate = new Post(postCreationRequest.getTitle(), LocalDateTime.now(), postCreationRequest.getContent());
        loggedUser.addPost(postToCreate);
        ResponseService response = new ResponseService("success", "post created", postToCreate);
        postRepository.save(postToCreate);
        userService.saveUser(loggedUser);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/like/{postId}")
    public ResponseEntity<ResponseService> likePost (@PathVariable Long postId) {
        AppUser loggedUser = userService.findLoggedUser();
        Post postToLike = postRepository.findPostByIdElseThrow(postId);
        loggedUser.likePost(postToLike);
        postRepository.save(postToLike);
        userService.saveUser(loggedUser);
        singleUserNotificationService.notifyUserAboutNewPostLike(postToLike);
        return ResponseEntity.ok(new ResponseService("success", "post liked", postToLike));
    }
}