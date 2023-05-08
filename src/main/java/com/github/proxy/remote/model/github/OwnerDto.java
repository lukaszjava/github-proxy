package com.github.proxy.remote.model.github;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(onConstructor_ = @JsonCreator)
@Getter
public class OwnerDto {
    private final String login;
}
