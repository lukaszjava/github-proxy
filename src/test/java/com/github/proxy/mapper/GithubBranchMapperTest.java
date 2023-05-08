package com.github.proxy.mapper;

import com.github.proxy.TestDataProvider;
import com.github.proxy.model.dto.BranchInformationDto;
import com.github.proxy.remote.model.github.BranchDto;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class GithubBranchMapperTest {

    private final GithubBranchMapper mapper = Mappers.getMapper(GithubBranchMapper.class);

    @ParameterizedTest
    @MethodSource("branchDtoProvider")
    public void testToBranchInformation(BranchDto branchDto, String expectedName, String expectedSha) {
        BranchInformationDto result = mapper.toBranchInformation(branchDto);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(expectedName);
        assertThat(result.getSha()).isEqualTo(expectedSha);
    }

    @ParameterizedTest
    @MethodSource("branchDtoListProvider")
    public void testToBranchInformationList(List<BranchDto> branches, int expectedSize, String expectedFirstName, String expectedFirstSha, String expectedSecondName, String expectedSecondSha) {
        List<BranchInformationDto> result = mapper.toBranchInformation(branches);

        assertThat(result).isNotNull();
        assertThat(result).hasSize(expectedSize);
        assertThat(result.get(0).getName()).isEqualTo(expectedFirstName);
        assertThat(result.get(1).getName()).isEqualTo(expectedSecondName);
        assertThat(result.get(0).getSha()).isEqualTo(expectedFirstSha);
        assertThat(result.get(1).getSha()).isEqualTo(expectedSecondSha);
    }

    private static Stream<Arguments> branchDtoProvider() {
        BranchDto branchDto1 = TestDataProvider.createBranch("abc", "cdb");
        BranchDto branchDto2 = TestDataProvider.createBranch("def", "efg");

        return Stream.of(
                Arguments.of(branchDto1, "abc", "cdb"),
                Arguments.of(branchDto2, "def", "efg")
        );
    }

    private static Stream<Arguments> branchDtoListProvider() {
        List<BranchDto> branches1 = TestDataProvider.getBranches();
        List<BranchDto> branches2 = List.of(
                TestDataProvider.createBranch(null, "123"),
                TestDataProvider.createBranch("jkl", null)
        );

        return Stream.of(
                Arguments.of(branches1, 2, "b1", "bnb123", "b2", "b213321"),
                Arguments.of(branches2, 2, null, "123", "jkl", null)
        );
    }
}