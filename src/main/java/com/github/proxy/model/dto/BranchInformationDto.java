package com.github.proxy.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BranchInformationDto {
    private String name;
    private String sha;
}
