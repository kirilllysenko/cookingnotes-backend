package com.kirill.cookingnotes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message, String errorCode) {
        super(message);
    }

    public BadRequestException(String message, String errorCode,  Throwable cause) {
        super(message, cause);
    }
}
