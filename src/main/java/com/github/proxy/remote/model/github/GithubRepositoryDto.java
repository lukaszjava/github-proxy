package com.github.proxy.remote.model.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GithubRepositoryDto {
    private String name;
    @JsonProperty("fork")
    private boolean isFork;
    @JsonProperty("owner")
    private OwnerDto ownerDto;
}
