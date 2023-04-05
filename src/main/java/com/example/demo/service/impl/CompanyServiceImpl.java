package com.example.demo.service.impl;

import static com.example.demo.util.Utils.generatePermalink;

import com.example.demo.controller.dto.CompanyRequestDto;
import com.example.demo.entity.Company;
import com.example.demo.entity.Contact;
import com.example.demo.exception.CompanyNotFoundException;
import com.example.demo.exception.ContactNotFoundException;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.ContactRepository;
import com.example.demo.service.CompanyService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final ContactRepository contactRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository, ContactRepository contactRepository) {
        this.companyRepository = companyRepository;
        this.contactRepository = contactRepository;
    }

    @Override
    public Company getByPermalink(String permalink) throws CompanyNotFoundException {
        Company company = companyRepository.findByPermalink(permalink);
        if (company == null) {
            throw new CompanyNotFoundException("Company [" + permalink + "] not found");
        }
        return company;
    }

    @Override
    public Company create(CompanyRequestDto companyRequestDto) throws ContactNotFoundException {
        Company company = new Company();
        company.setTitle(companyRequestDto.getTitle());
        company.setDescription(companyRequestDto.getDescription());
        company.setPermalink(generatePermalink(companyRequestDto.getTitle()));

        List<Contact> contacts = new ArrayList<>();
        if (companyRequestDto.getContacts() != null && !companyRequestDto.getContacts().isEmpty()) {
            contacts = contactRepository.findAllByIdIn(companyRequestDto.getContacts());
            if (contacts.size() != companyRequestDto.getContacts().size()) {
                List<Contact> copyContacts = contacts;
                List<Long> notFoundContacts = companyRequestDto.getContacts().stream()
                        .filter(id -> copyContacts.stream().noneMatch(contact -> contact.getId().equals(id)))
                        .collect(Collectors.toList());
                throw new ContactNotFoundException("Contact [" + Arrays.toString(notFoundContacts.toArray()) + "] not found");
            }
        }
        company.setContacts(contacts);
        return companyRepository.save(company);
    }
}
