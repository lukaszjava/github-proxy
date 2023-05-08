package com.github.proxy.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(onConstructor_ = {@JsonCreator})
@Getter
public class GithubProxyMessageDto {

    private final int status;
    @JsonProperty("Message")
    private final String message;

    public static GithubProxyMessageDto from(int status, String message) {
        return new GithubProxyMessageDto(status, message);
    }

}
