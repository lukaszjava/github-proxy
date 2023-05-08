package com.github.proxy.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor(onConstructor_ = @JsonCreator)
@Builder
@Getter
public class UserRepositoryDetailDto {
    private final String repositoryName;
    private final String ownerLogin;
    private final List<BranchInformationDto> branches;
}
