package com.github.proxy.mapper;

import com.github.proxy.model.dto.UserRepositoryDetailDto;
import com.github.proxy.remote.model.github.GithubRepositoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GithubRepositoryMapper {
    @Mapping(source = "name", target = "repositoryName")
    @Mapping(source = "ownerDto.login", target = "ownerLogin")
    UserRepositoryDetailDto toUserRepositoryDetailDto(GithubRepositoryDto githubRepository);
}
