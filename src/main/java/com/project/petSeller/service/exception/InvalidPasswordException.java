package com.project.petSeller.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST) // Статус 400 за невалидни данни
public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {

        super("Password must be at least 5 characters long.");
    }
}
