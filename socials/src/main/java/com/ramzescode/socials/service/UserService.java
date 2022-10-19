package com.ramzescode.socials.service;

import com.ramzescode.socials.DTO.RegistrationRequest;
import com.ramzescode.socials.model.User;
import com.ramzescode.socials.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

    public ResponseService findUserById(Long id) {
        ResponseService response = new ResponseService();
        Optional<User> userOptional = userRepository.findById(id);
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


    public ResponseService saveUser(RegistrationRequest inputUser) {
        ResponseService response = new ResponseService();
        Optional<User> userOptional = userRepository.findUserByEmail(inputUser.getEmail());
        if (userOptional.isPresent()) {
            response.setStatus("fail");
            response.setPayload(null);
            response.setMessage("Email address " + inputUser.getEmail() + " is already in use");
            return response;
        }
        else {
            User userToSave = inputUser.toUser();
            userToSave.setPassword(encoder.encode(inputUser.getPassword()));
            User savedUser = userRepository.save(userToSave);
            response.setMessage("User created");
            response.setPayload(savedUser);
            response.setStatus("success");
            return response;
        }
    }
}
