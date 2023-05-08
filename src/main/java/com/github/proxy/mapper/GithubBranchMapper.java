package com.github.proxy.mapper;

import com.github.proxy.model.dto.BranchInformationDto;
import com.github.proxy.remote.model.github.BranchDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GithubBranchMapper {

    @Mapping(source = "lastCommit.sha", target = "sha")
    BranchInformationDto toBranchInformation(BranchDto branchDto);

    List<BranchInformationDto> toBranchInformation(List<BranchDto> branches);

}
