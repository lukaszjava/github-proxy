package com.github.proxy.controller;

import com.github.proxy.model.dto.GithubProxyMessageDto;
import com.github.proxy.model.dto.UserRepositoryDetailDto;
import com.github.proxy.service.GithubProxyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/repos")
public class GithubProxyController {

    private final GithubProxyService githubProxyService;

    @Operation(summary = "Get information about user repositories", tags = "Github Proxy")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserRepositoryDetailDto[].class))}),
            @ApiResponse(responseCode = "406", description = "Accept: application/xml header is not supported",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GithubProxyMessageDto.class))})
    })
    @GetMapping(path = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    private List<UserRepositoryDetailDto> getRepositoriesInformation(@PathVariable("username") String username) {
        return githubProxyService.getRepositoriesInformation(username);
    }

}
