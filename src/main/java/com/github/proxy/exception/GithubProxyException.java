package com.github.proxy.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GithubProxyException extends RuntimeException {

    private final HttpStatus httpStatus;

    public GithubProxyException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
