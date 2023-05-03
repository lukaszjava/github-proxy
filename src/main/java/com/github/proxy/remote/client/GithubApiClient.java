package com.github.proxy.remote.client;

import com.github.proxy.remote.model.github.BranchDto;
import com.github.proxy.remote.model.github.GithubRepositoryDto;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "githubApiClient", url = "https://api.github.com")
@Headers("Accept: application/vnd.github+json")
public interface GithubApiClient {

    @RequestMapping(method = RequestMethod.GET, value = "/users/{username}/repos", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<GithubRepositoryDto>> getRepository(@PathVariable("username") String username);

    @RequestMapping(method = RequestMethod.GET, value = "/repos/{username}/{repositoryName}/branches", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<BranchDto>> getBranches(@PathVariable("username") String username, @PathVariable("repositoryName") String repositoryName);

}
