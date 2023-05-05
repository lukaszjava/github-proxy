package com.github.proxy.remote.model.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BranchDto {
    private final String name;
    @JsonProperty("commit")
    private final CommitDto lastCommit;
}
