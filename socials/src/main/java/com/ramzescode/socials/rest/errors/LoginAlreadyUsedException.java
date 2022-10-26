package com.ramzescode.socials.rest.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LoginAlreadyUsedException extends RuntimeException {

    public LoginAlreadyUsedException(String message) {
        super("Username: " + message + " is already in use.");
    }
}
