package com.ramzescode.socials.rest;

import com.ramzescode.socials.DTO.LoginRequest;
import com.ramzescode.socials.DTO.RegistrationRequest;
import com.ramzescode.socials.jwt.JwtUtil;
import com.ramzescode.socials.service.ResponseService;
import com.ramzescode.socials.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final JwtUtil  jwtUtil;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }


    @PostMapping("/signup")
    public ResponseEntity<ResponseService> registerUser(@RequestBody RegistrationRequest inputUser) {
        return ResponseEntity.ok(userService.registerUser(inputUser));
    }

    @GetMapping("/ping")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello");
    }

    @GetMapping("/ping/admin")
    public ResponseEntity<String> helloAdmin() {
        return ResponseEntity.ok("hello admin");
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        authenticationManager.authenticate(token);
        String jwtToken = jwtUtil.generateAccessToken(loginRequest.getUsername());
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", jwtToken);
        return ResponseEntity.ok(tokens);
    }


}
