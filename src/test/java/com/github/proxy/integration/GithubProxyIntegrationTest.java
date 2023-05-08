package com.github.proxy.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.proxy.model.dto.UserRepositoryDetailDto;
import com.github.proxy.remote.model.github.BranchDto;
import com.github.proxy.remote.model.github.CommitDto;
import com.github.proxy.remote.model.github.GithubRepositoryDto;
import com.github.proxy.remote.model.github.OwnerDto;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = {"server.port=8887"})
@AutoConfigureWireMock(port = 7070)
public class GithubProxyIntegrationTest {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    WireMockServer mockServer;

    @Test
    public void testGetRepositoriesInformation() throws Exception {
        String username = "testuser";
        String repositoryName = "testrepo";
        String branchName = "testbranch";
        GithubRepositoryDto githubRepositoryDto = new GithubRepositoryDto(repositoryName, false, new OwnerDto(username));
        BranchDto branchDto = new BranchDto(branchName, new CommitDto("dsad", "dsdsds"));

        mockServer.stubFor(get(urlEqualTo("/users/" + username + "/repos"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(Collections.singletonList(githubRepositoryDto)))));

        mockServer.stubFor(get(urlEqualTo("/repos/" + username + "/" + repositoryName + "/branches"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(Collections.singletonList(branchDto)))));


        ResponseEntity<List<UserRepositoryDetailDto>> responseEntity = restTemplate.exchange(getUrl(username), HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        Assertions.assertAll(
                () -> Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatusCode.valueOf(200)),
                () -> Assertions.assertEquals(1, responseEntity.getBody().size()),
                () -> Assertions.assertEquals(repositoryName, responseEntity.getBody().get(0).getRepositoryName()),
                () -> Assertions.assertEquals(1, responseEntity.getBody().get(0).getBranches().size()),
                () -> Assertions.assertEquals(branchName, responseEntity.getBody().get(0).getBranches().get(0).getName())
        );
    }

    @Test
    public void testGetRepositoriesInformationWithNotFound() {
        String username = "nonexistent";

        stubFor(get(urlEqualTo("/users/" + username + "/repos"))
                .willReturn(aResponse().withStatus(404)));

        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
                () -> restTemplate.exchange(getUrl(username), HttpMethod.GET, null, new ParameterizedTypeReference<>() {})
        );

        assertThat(exception.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(404));
        assertThat(exception.getMessage()).contains("{\"status\":404,\"Message\":\"[404 Not Found]");
    }

    private String getUrl(String username) {
        return String.format("http://localhost:8887/github-proxy/repos/%s", username);
    }
}
