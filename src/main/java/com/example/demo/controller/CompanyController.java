package com.example.demo.controller;

import com.example.demo.controller.dto.CompanyRequestDto;
import com.example.demo.entity.Company;
import com.example.demo.exception.CompanyNotFoundException;
import com.example.demo.exception.ContactNotFoundException;
import com.example.demo.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@RestController
@RequestMapping("/v1/company")
@Validated
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/{permalink}")
    public Company get(@PathVariable("permalink") @NotBlank @Size(min = 5, max = 99) String permalink) throws CompanyNotFoundException {
        return companyService.getByPermalink(permalink);
    }

    @PostMapping("/create")
    public Company create(@Valid @RequestBody CompanyRequestDto request) throws ContactNotFoundException {
        return companyService.create(request);
    }
}
