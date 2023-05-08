package com.github.proxy.handler;

import com.github.proxy.exception.GithubUserDoesNotExistException;
import com.github.proxy.model.dto.GithubProxyMessageDto;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GithubProxyExceptionHandler {

    @ExceptionHandler(FeignException.NotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<GithubProxyMessageDto> forecastExceptionErrorResponse(FeignException.NotFound forecastException) {
        return ResponseEntity.status(forecastException.status()).body(GithubProxyMessageDto.from(forecastException.status(), forecastException.getMessage()));
    }

    @ExceptionHandler(GithubUserDoesNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<GithubProxyMessageDto> githubUserDoesNotExistErrorResponse(GithubUserDoesNotExistException githubUserDoesNotExistException) {
        return ResponseEntity.status(githubUserDoesNotExistException.getHttpStatus().value())
                .body(GithubProxyMessageDto.from(githubUserDoesNotExistException.getHttpStatus().value(), githubUserDoesNotExistException.getMessage()));
    }

}
