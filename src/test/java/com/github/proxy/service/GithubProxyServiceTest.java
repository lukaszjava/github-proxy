package com.github.proxy.service;

import com.github.proxy.exception.GithubProxyException;
import com.github.proxy.exception.GithubUserDoesNotExistException;
import com.github.proxy.mapper.GithubBranchMapper;
import com.github.proxy.mapper.GithubRepositoryMapper;
import com.github.proxy.model.dto.UserRepositoryDetailDto;
import com.github.proxy.remote.client.GithubApiClient;
import com.github.proxy.remote.model.github.BranchDto;
import com.github.proxy.remote.model.github.GithubRepositoryDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.github.proxy.TestDataProvider.createBranch;
import static com.github.proxy.TestDataProvider.createGithubRepositoryDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class GithubProxyServiceTest {

    private GithubApiClient githubApiClient;
    private GithubRepositoryMapper githubRepositoryMapper;
    private GithubBranchMapper githubBranchMapper;
    private GithubProxyService githubProxyService;

    @BeforeEach
    void setUp() {
        this.githubApiClient = Mockito.mock(GithubApiClient.class);
        this.githubRepositoryMapper = Mappers.getMapper(GithubRepositoryMapper.class);
        this.githubBranchMapper = Mappers.getMapper(GithubBranchMapper.class);
        this.githubProxyService = new GithubProxyService(githubApiClient, githubRepositoryMapper, githubBranchMapper);
    }

    @Test
    void getRepositoriesInformation_WhenUserDoesNotExist_ThrowsGithubUserDoesNotExistException() {
        String username = "not_found";
        when(githubApiClient.getRepository(username)).thenReturn(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));

        assertThrows(GithubUserDoesNotExistException.class, () -> githubProxyService.getRepositoriesInformation(username));
    }

    @Test
    void getRepositoriesInformation_WhenApiReturnsNullBody_ThrowsGithubProxyException() {
        String username = "test-user";
        when(githubApiClient.getRepository(username)).thenReturn(new ResponseEntity<>(null, HttpStatus.OK));

        assertThrows(GithubProxyException.class, () -> githubProxyService.getRepositoriesInformation(username));
    }

    @Test
    void getRepositoriesInformation_ApiReturnsValidData_ReturnsData() {
        String username = "test-user";
        List<GithubRepositoryDto> repositoryList = List.of(
                createGithubRepositoryDto("test-name", false, "testdev1"),
                createGithubRepositoryDto("test-name3", true, "testdev")
        );
        List<BranchDto> branches = List.of(
                createBranch("b1", "bnb123"),
                createBranch("b2", "b213321")
        );
        when(githubApiClient.getRepository(eq(username))).thenReturn(ResponseEntity.ok(repositoryList));
        when(githubApiClient.getBranches(eq(username), anyString())).thenReturn(ResponseEntity.ok(branches));

        List<UserRepositoryDetailDto> resultList = githubProxyService.getRepositoriesInformation(username);

        assertEquals(1, resultList.size());
        assertEquals("test-name", resultList.get(0).getRepositoryName());
        assertEquals("testdev1", resultList.get(0).getOwnerLogin());
    }

}