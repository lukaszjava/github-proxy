package com.github.proxy.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.proxy.remote.client.GithubApiClient;
import com.github.proxy.remote.model.github.BranchDto;
import com.github.proxy.remote.model.github.CommitDto;
import com.github.proxy.remote.model.github.GithubRepositoryDto;
import com.github.proxy.remote.model.github.OwnerDto;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatusCode;

import java.util.Collections;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = {"server.port=8887"})
@AutoConfigureWireMock(port = 7070)
public class GithubApiClientTest {

    @Autowired
    GithubApiClient githubApiClient;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    WireMockServer mockServer;

    @Test
    public void getRepositories_UserExists_DataReturned() throws Exception {
        String username = "testuser";
        String repositoryName = "testrepo";
        GithubRepositoryDto githubRepositoryDto = new GithubRepositoryDto(repositoryName, false, new OwnerDto(username));

        mockServer.stubFor(get(urlEqualTo("/users/" + username + "/repos"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(Collections.singletonList(githubRepositoryDto)))));

        var response = githubApiClient.getRepositories(username);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(response.getBody())
                .isNotNull()
                .hasSize(1)
                .extracting(GithubRepositoryDto::getName, GithubRepositoryDto::isFork)
                .contains(Tuple.tuple(repositoryName, false));
    }

    @Test
    public void getBranches_CorrectVariables_DataReturned() throws Exception {
        String username = "testuser";
        String repositoryName = "testrepo";
        String branchName = "testbranch";
        BranchDto branchDto = new BranchDto(branchName, new CommitDto("dsad", "dsdsds"));

        mockServer.stubFor(get(urlEqualTo("/repos/" + username + "/" + repositoryName + "/branches"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(Collections.singletonList(branchDto)))));

        var response = githubApiClient.getBranches(username, repositoryName);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(response.getBody())
                .isNotNull()
                .hasSize(1)
                .extracting(BranchDto::getName, branch -> branch.getLastCommit().getSha())
                .contains(Tuple.tuple(branchName, "dsad"));
    }

}
