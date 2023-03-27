package com.example.demo.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ProjectFilterRequestDto {

    @NotBlank
    private String area;

    private String status;
}
