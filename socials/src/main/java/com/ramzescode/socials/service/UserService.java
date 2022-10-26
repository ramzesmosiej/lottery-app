package com.ramzescode.socials.service;

import com.ramzescode.socials.DTO.RegistrationRequest;
import com.ramzescode.socials.domain.AppUser;
import com.ramzescode.socials.domain.Role;
import com.ramzescode.socials.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private BCryptPasswordEncoder encoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
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


    public ResponseService registerUser(RegistrationRequest inputUser) {
        ResponseService response = new ResponseService();
        Optional<AppUser> userOptional = userRepository.findAppUserByUsername(inputUser.getUsername());
        if (userOptional.isPresent()) {
            response.setStatus("fail");
            response.setPayload(null);
            response.setMessage("Username " + inputUser.getUsername() + " is already in use");
        }
        else {
            AppUser appUserToSave = inputUser.toUser();
            appUserToSave.setPassword(encoder.encode(inputUser.getPassword()));
            appUserToSave.setRoles(new ArrayList<>(List.of(new Role("ROLE_USER"))));
            AppUser savedAppUser = userRepository.save(appUserToSave);
            response.setMessage("User created");
            response.setPayload(savedAppUser);
            response.setStatus("success");
        }
        return response;
    }

    public void saveUser(AppUser user) {
        userRepository.save(user);
    }

}
