package com.programacion4.unidad3ej3.config.exceptions;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public abstract class CustomException extends RuntimeException {
    
    private final HttpStatus status;
    private final List<String> errors;

    public CustomException(String message, HttpStatus status, List<String> errors) {
        super(message);
        this.status = status != null ? status : HttpStatus.BAD_REQUEST;
        this.errors = errors != null && !errors.isEmpty() ? errors : List.of(message);
    }

    public CustomException(String message, HttpStatus status) {
        this(message, status, List.of(message));
    }

}
