package com.ramzescode.socials.service;

import com.ramzescode.socials.DTO.RegistrationRequest;
import com.ramzescode.socials.domain.AppUser;
import com.ramzescode.socials.domain.Post;
import com.ramzescode.socials.domain.Role;
import com.ramzescode.socials.repository.FollowingRepository;
import com.ramzescode.socials.repository.PostRepository;
import com.ramzescode.socials.repository.UserRepository;
import com.ramzescode.socials.rest.errors.LoginAlreadyUsedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;

    private BCryptPasswordEncoder encoder;

    private final PostRepository postRepository;

    private final FollowingRepository followingRepository;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder, PostRepository postRepository, FollowingRepository followingRepository) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.postRepository = postRepository;
        this.followingRepository = followingRepository;
    }

    public ResponseService findAll() {
        ResponseService response = new ResponseService();
        response.setStatus("success");
        response.setMessage("Returning all users");
        response.setPayload(userRepository.findAll());
        return response;
    }

    public ResponseService findCurrentlyLoggedUser() {
        ResponseService response = new ResponseService();
        AppUser appUser = findLoggedUser();
        response.setPayload(appUser);
        response.setStatus("success");
        response.setMessage("Logged user found");
        return response;
    }

    public AppUser findLoggedUser() {
        return userRepository.getLoggedUser();
    }

    public ResponseService findUserByIdWithResponse(Long id) {
        ResponseService response = new ResponseService();
        Optional<AppUser> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            response.setStatus("success");
            response.setMessage("User found");
            response.setPayload(userOptional.get());
        }
        else {
            response.setStatus("fail");
            response.setMessage("User with id: " + id + " not found");
            response.setPayload(null);
        }
        return response;
    }

    public AppUser findUserById(Long id) {
        return userRepository.findAppUserByIdOrElseThrow(id);
    }


    public AppUser registerUser(RegistrationRequest inputUser) {
        Optional<AppUser> userOptional = userRepository.findAppUserByUsername(inputUser.getUsername());
        if (userOptional.isPresent()) {
            throw new LoginAlreadyUsedException(inputUser.getUsername());
        }
        else {
            AppUser appUserToSave = inputUser.toUser();
            appUserToSave.setPassword(encoder.encode(inputUser.getPassword()));
            appUserToSave.setRoles(new HashSet<>(Set.of(new Role("ROLE_USER"))));
            return userRepository.save(appUserToSave);
        }
    }

    public void saveUser(AppUser user) {
        userRepository.save(user);
    }

    public void deleteUserByUsername(String username) {
        userRepository.findAppUserByUsername(username).ifPresent(this::deleteUser);
    }

    /*
    trying to avoid CascadeType.REMOVE and CascadeType.ALL,
    cause the relationships with
    post is @OneToMany and @ManyToMany with followers and postLikes.
    Therefore, the implementation of removal of child entities is
    done here by iterating over all of them.
     */

    private void deleteUser(AppUser user) {

        postRepository.deleteAllByAppUser(user);

        user.deleteAllPostLikes();
        postRepository.findAll()
                .forEach(post -> {
                    post.deleteLikeByUsername(user.getUsername());
                    postRepository.save(post);
                });
        followingRepository.findAll()
                .stream()
                .filter(followingRelationship -> followingRelationship.getFollower().getUsername().equals(user.getUsername()))
                .filter(followingRelationship -> followingRelationship.getFollowing().getUsername().equals(user.getUsername()))
                .forEach(followingRepository::delete);

        userRepository.delete(user);
    }

}
