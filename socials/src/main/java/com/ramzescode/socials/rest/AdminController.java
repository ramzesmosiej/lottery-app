package com.ramzescode.socials.rest;

import com.ramzescode.socials.domain.AppUser;
import com.ramzescode.socials.repository.FollowingRepository;
import com.ramzescode.socials.repository.UserRepository;
import com.ramzescode.socials.rest.errors.BadRequestException;
import com.ramzescode.socials.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserRepository userRepository;

    private final UserService userService;

    public AdminController(UserRepository userRepository, FollowingRepository followingRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        Optional<AppUser> userToDelete = userRepository.findAppUserByUsername(username);
        if (username.equals(userRepository.getLoggedUser().getUsername()) || userToDelete.isEmpty()) {
            throw new BadRequestException("You cannot delete yourself or user does not exist.");
        }
        userService.deleteUserByUsername(username);

        return ResponseEntity.ok(username);
    }

}
