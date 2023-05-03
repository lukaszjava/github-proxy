package com.github.proxy.service;

import com.github.proxy.exception.GithubProxyException;
import com.github.proxy.exception.GithubUserDoesNotExistException;
import com.github.proxy.mapper.GithubBranchMapper;
import com.github.proxy.mapper.GithubRepositoryMapper;
import com.github.proxy.model.dto.UserRepositoryDetailDto;
import com.github.proxy.remote.client.GithubApiClient;
import com.github.proxy.remote.model.github.GithubRepositoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GithubProxyService {

    private final GithubApiClient githubApiClient;
    private final GithubRepositoryMapper githubRepositoryMapper;
    private final GithubBranchMapper githubBranchMapper;

    public List<UserRepositoryDetailDto> getRepositoriesInformation(String username) {
        ResponseEntity<List<GithubRepositoryDto>> githubRepositoriesResponse = githubApiClient.getRepository(username);

        if (githubRepositoriesResponse.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(404))) {
            throw new GithubUserDoesNotExistException(String.format("User: %s does not exist.", username));
        }
        if (Objects.isNull(githubRepositoriesResponse.getBody())) {
            throw new GithubProxyException(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown error occurred. Try again later.");
        }

        return githubRepositoriesResponse.getBody().parallelStream()
                .filter(repository -> !repository.isFork())
                .map(repository -> {
                    var repositoryDetails = githubRepositoryMapper.toUserRepositoryDetailDto(repository);
                    var branches = githubApiClient.getBranches(username, repository.getName());
                    repositoryDetails.setBranches(githubBranchMapper.toBranchInformation(branches.getBody()));
                    return repositoryDetails;
                })
                .collect(Collectors.toList());
    }
}
