package com.github.proxy.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GithubProxyMessageDto {

    private int status;
    @JsonProperty("Message")
    private String message;

    public static GithubProxyMessageDto from(int status, String message) {
        return new GithubProxyMessageDto(status, message);
    }

}
