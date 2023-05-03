package com.github.proxy.controller;

import com.github.proxy.remote.client.GithubApiClient;
import com.github.proxy.remote.model.github.BranchDto;
import com.github.proxy.remote.model.github.GithubRepositoryDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static com.github.proxy.TestDataProvider.getBranches;
import static com.github.proxy.TestDataProvider.getGithubRepositories;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
class GithubProxyControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GithubApiClient githubApiClient;

    @Test
    void getRepositoriesInformation_ReturnsListOfUserRepositoryDetailDto() throws Exception {
        String username = "test-user";
        List<GithubRepositoryDto> repositoryList = getGithubRepositories();
        List<BranchDto> branches = getBranches();
        when(githubApiClient.getRepository(eq(username))).thenReturn(ResponseEntity.ok(repositoryList));
        when(githubApiClient.getBranches(eq(username), anyString())).thenReturn(ResponseEntity.ok(branches));

        mockMvc.perform(MockMvcRequestBuilders.get("/repos/" + username)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].repositoryName").value("test-name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].ownerLogin").value("testdev1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].branches[0].sha").value("bnb123"));
    }

    @Test
    void getRepositoriesInformation_WrongHeaderProvided() throws Exception {
        String username = "test-user";

        mockMvc.perform(MockMvcRequestBuilders.get("/repos/" + username)
                        .accept(MediaType.APPLICATION_XML_VALUE))
                .andExpect(status().is(406))
                .andExpect(MockMvcResultMatchers.jsonPath("$.Message").value("Invalid Accept header, 'application/xml' is not supported."))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("406"));
    }
}
