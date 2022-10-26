package com.ramzescode.socials.rest;


import com.ramzescode.socials.service.ResponseService;
import com.ramzescode.socials.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
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
        return ResponseEntity.ok(userService.findUserByIdWithResponse(id));
    }

    @GetMapping("/logged")
    public ResponseEntity<ResponseService> findCurrentlyLoggedUser() {
        return ResponseEntity.ok(userService.findCurrentlyLoggedUser());
    }




}
