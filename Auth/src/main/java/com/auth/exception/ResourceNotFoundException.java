package com.auth.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends RuntimeException {
    private String message;
    private HttpStatus status;
    public ResourceNotFoundException(String message){
        this.message=message;
        this.status=HttpStatus.NOT_FOUND;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
