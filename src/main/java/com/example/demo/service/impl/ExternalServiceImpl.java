package com.example.demo.service.impl;

import com.example.demo.entity.Project;
import com.example.demo.exception.ExternalServiceException;
import com.example.demo.service.ExternalService;
import org.springframework.stereotype.Service;

@Service
public class ExternalServiceImpl implements ExternalService {

    @Override
    public boolean saveProject(Project project) throws ExternalServiceException {
        if (project.getTitle().length() > 10) {
            throw new ExternalServiceException("Error while saving project [" + project.getTitle() + "]");
        }
        return true;
    }
}
