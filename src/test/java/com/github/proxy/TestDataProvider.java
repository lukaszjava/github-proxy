package com.github.proxy;

import com.github.proxy.remote.model.github.BranchDto;
import com.github.proxy.remote.model.github.CommitDto;
import com.github.proxy.remote.model.github.GithubRepositoryDto;
import com.github.proxy.remote.model.github.OwnerDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestDataProvider {

    public static GithubRepositoryDto createGithubRepositoryDto(String name, boolean fork, String ownerLogin) {
        var owner = new OwnerDto(ownerLogin);
        return new GithubRepositoryDto(name, fork, owner);
    }

    public static BranchDto createBranch(String name, String sha) {
        return new BranchDto(name, new CommitDto(sha, null));
    }

    public static List<GithubRepositoryDto> getGithubRepositories() {
        return List.of(
                createGithubRepositoryDto("test-name", false, "testdev1"),
                createGithubRepositoryDto("test-name3", true, "testdev")
        );
    }

    public static List<BranchDto> getBranches() {
        return List.of(
                createBranch("b1", "bnb123"),
                createBranch("b2", "b213321")
        );
    }

}
