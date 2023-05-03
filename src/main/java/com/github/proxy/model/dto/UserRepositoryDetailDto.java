package com.github.proxy.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class UserRepositoryDetailDto {
    private String repositoryName;
    private String ownerLogin;
    private List<BranchInformationDto> branches;
}
