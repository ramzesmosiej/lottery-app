package com.ramzescode.socials.service;

import com.ramzescode.socials.DTO.RegistrationRequest;
import com.ramzescode.socials.domain.AppUser;
import com.ramzescode.socials.repository.UserRepository;
import com.ramzescode.socials.security.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.ramzescode.socials.security.Role.APP_USER;

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


    public ResponseService saveUser(RegistrationRequest inputUser) {
        ResponseService response = new ResponseService();
        Optional<AppUser> userOptional = userRepository.findUserByEmail(inputUser.getEmail());
        if (userOptional.isPresent()) {
            response.setStatus("fail");
            response.setPayload(null);
            response.setMessage("Email address " + inputUser.getEmail() + " is already in use");
        }
        else {
            AppUser appUserToSave = inputUser.toUser();
            appUserToSave.setPassword(encoder.encode(inputUser.getPassword()));
            appUserToSave.getAuthorities().addAll(APP_USER.getGrantedAuthorities());
            AppUser savedAppUser = userRepository.save(appUserToSave);
            response.setMessage("User created");
            response.setPayload(savedAppUser);
            response.setStatus("success");
        }
        return response;
    }

    public Set<SimpleGrantedAuthority> getUserAuthorities(String username) {
        AppUser user = userRepository.findAppUserByUsername(username).get();
        return user.getAuthorities();
    }
}
