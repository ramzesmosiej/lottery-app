package com.ramzescode.socials.controller;


import com.ramzescode.socials.DTO.LoginRequest;
import com.ramzescode.socials.DTO.RegistrationRequest;
import com.ramzescode.socials.jwt.JwtUtil;
import com.ramzescode.socials.service.ResponseService;
import com.ramzescode.socials.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    private final UserService userService;

    private final JwtUtil jwtUtil;

    private final AuthenticationManager authenticationManager;
    public UserController(UserService userService, JwtUtil jwtUtil ,AuthenticationManager authenticationManager
    ) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/users")
    public ResponseEntity<ResponseService> findAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("users/{id}")
    public ResponseEntity<ResponseService> findUserById(@PathVariable Long id ) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @PostMapping("/auth/signup")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<ResponseService> registerUser(@RequestBody RegistrationRequest inputUser) {
        return ResponseEntity.ok(userService.saveUser(inputUser));
    }

    @GetMapping("/ping")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello");
    }

    @PostMapping("/auth/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        authenticationManager.authenticate(token);
        String jwtToken = jwtUtil.generateAccessToken(loginRequest.getUsername());
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", jwtToken);
        return ResponseEntity.ok(tokens);
    }
}
