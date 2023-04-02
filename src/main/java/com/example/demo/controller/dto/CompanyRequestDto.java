package com.example.demo.controller.dto;

import com.example.demo.validator.annotation.ValidUniqueInList;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class CompanyRequestDto {

    @NotBlank
    @Size(min = 5, max = 250)
    private String title;

    @NotBlank
    @Size(min = 5, max = 250)
    private String description;

    @ValidUniqueInList
    private List<Long> contacts;
}
