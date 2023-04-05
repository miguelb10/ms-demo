package com.example.demo.service;

import com.example.demo.controller.dto.CompanyRequestDto;
import com.example.demo.entity.Company;
import com.example.demo.exception.CompanyNotFoundException;
import com.example.demo.exception.ContactNotFoundException;

public interface CompanyService {

    Company getByPermalink(String permalink) throws CompanyNotFoundException;

    Company create(CompanyRequestDto company) throws ContactNotFoundException;
}
