package com.example.demo.service;

import com.example.demo.entity.Project;
import com.example.demo.exception.ExternalServiceException;

public interface ExternalService {

    boolean saveProject(Project project) throws ExternalServiceException;
}
