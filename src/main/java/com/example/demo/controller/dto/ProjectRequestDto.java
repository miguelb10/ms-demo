package com.example.demo.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ProjectRequestDto {

    @NotBlank
    @Size(max = 250)
    private String title;

    @NotBlank
    @Size(max = 250)
    private String description;
}
