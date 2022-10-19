package com.ramzescode.socials.controller;


import com.ramzescode.socials.DTO.RegistrationRequest;
import com.ramzescode.socials.repository.UserRepository;
import com.ramzescode.socials.service.ResponseService;
import com.ramzescode.socials.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public ResponseEntity<ResponseService> findAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseService> findUserById(@PathVariable Long id ) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<ResponseService> registerUser(@RequestBody RegistrationRequest inputUser) {
        return ResponseEntity.ok(userService.saveUser(inputUser));
    }
}
