package com.ramzescode.socials.service;

import com.ramzescode.socials.domain.AppUser;
import com.ramzescode.socials.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findAppUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " was not found in database"));
        return new User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }
}
