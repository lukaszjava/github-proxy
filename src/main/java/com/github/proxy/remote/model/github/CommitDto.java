package com.github.proxy.remote.model.github;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommitDto {
    private String sha;
    private String url;
}
