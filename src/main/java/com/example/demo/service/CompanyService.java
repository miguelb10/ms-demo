package com.example.demo.service;

import com.example.demo.controller.dto.CompanyRequestDto;
import com.example.demo.entity.Company;
import com.example.demo.exception.ContactNotFoundException;

public interface CompanyService {

    Company getByPermalink(String permalink);

    Company create(CompanyRequestDto company) throws ContactNotFoundException;
}
