package com.github.proxy.remote.model.github;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(onConstructor_ = @JsonCreator)
@Getter
public class GithubRepositoryDto {
    private final String name;
    @JsonProperty("fork")
    private final boolean isFork;
    @JsonProperty("owner")
    private final OwnerDto ownerDto;
}
