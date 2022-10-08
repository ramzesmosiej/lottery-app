package com.authentication.authentication.controller;

import com.authentication.authentication.model.Authentication;
import com.authentication.authentication.model.InterviewRequest;
import com.authentication.authentication.repository.AuthenticationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationRepository repository;

    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);

    public AuthenticationController(AuthenticationRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<Authentication> interviewParticipant(@Valid @RequestBody InterviewRequest request) {
        Authentication createdAuth = new Authentication(request.getPesel(), LocalDateTime.now(), request.isPassed());
        repository.saveAndFlush(createdAuth);
        log.info("Authentication a person with pesel: " + request.getPesel());
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path(
                "/{pesel}"
        ).buildAndExpand(createdAuth.getId()).toUri()).body(createdAuth);
    }

    @GetMapping(path = "/allowed/{pesel}")
    public boolean isAuthenticated(@PathVariable String pesel) {
        return repository.existsByPesel(pesel);
    }

    @GetMapping(path = "/{pesel}")
    public ResponseEntity<Authentication> getInterview(@PathVariable String pesel) {
        Optional<Authentication> auth = repository.findByPesel(pesel);
        return auth.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
