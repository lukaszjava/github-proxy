package com.github.proxy.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(onConstructor_ = {@JsonCreator})
@Getter
public class BranchInformationDto {
    private final String name;
    private final String sha;
}
