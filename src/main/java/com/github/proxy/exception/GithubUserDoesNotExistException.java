package com.github.proxy.exception;

import org.springframework.http.HttpStatus;

public class GithubUserDoesNotExistException extends GithubProxyException {

    public GithubUserDoesNotExistException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

}
