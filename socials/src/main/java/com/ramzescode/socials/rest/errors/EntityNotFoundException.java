package com.ramzescode.socials.rest.errors;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String entityName, Long entityId) {
        super(entityName + " with id: \"" + entityId + "\" does not exist");
    }
}
