package com.ramzescode.socials.service;

import com.ramzescode.socials.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthoritiesService {

    private final UserRepository userRepository;

    public AuthoritiesService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<String> getUserAuthorities(String username) {
        List<String> authorities = new ArrayList<>();
        userRepository.findAppUserByUsername(username).get().getRoles().forEach(role -> {
            authorities.add(role.getName());
        });
        return authorities;
    }
}
