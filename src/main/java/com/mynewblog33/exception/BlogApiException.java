package com.mynewblog33.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class BlogApiException extends RuntimeException {
    private HttpStatus status;
    private String message;

    public BlogApiException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }
    @Override
    public String getMessage() {
        return message;
    }
}
