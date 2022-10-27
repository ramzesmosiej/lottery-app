package com.ramzescode.socials.rest;

import com.ramzescode.socials.domain.AppUser;
import com.ramzescode.socials.domain.FollowingRelationship;
import com.ramzescode.socials.repository.FollowingRepository;
import com.ramzescode.socials.service.SingleUserNotificationService;
import com.ramzescode.socials.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/follow")
public class FollowersController {

    private final UserService userService;

    private final FollowingRepository followingRepository;

    private final SingleUserNotificationService singleUserNotificationService;

    public FollowersController(UserService userService, FollowingRepository followingRepository, SingleUserNotificationService singleUserNotificationService) {
        this.userService = userService;
        this.followingRepository = followingRepository;
        this.singleUserNotificationService = singleUserNotificationService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<FollowingRelationship> followUser(@PathVariable Long id) {
        AppUser loggedUser = userService.findLoggedUser();
        AppUser toFollow = userService.findUserById(id);
        FollowingRelationship followingRelationship = new FollowingRelationship(loggedUser, toFollow);
        followingRepository.save(followingRelationship);
        singleUserNotificationService.notifyUserAboutNewFollower(followingRelationship);
        return ResponseEntity.ok(followingRelationship);
    }
}
