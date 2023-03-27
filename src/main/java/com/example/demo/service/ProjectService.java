package com.example.demo.service;

import com.example.demo.entity.Project;
import com.example.demo.exception.ExternalServiceException;
import com.example.demo.exception.ProjectNotFoundException;

import java.util.List;

public interface ProjectService {

    List<Project> getAll();

    Project getByPermalink(String permalink) throws ProjectNotFoundException;

    Project createProject(Project project) throws ExternalServiceException;

    Project updateProject(Project project) throws ExternalServiceException, ProjectNotFoundException;

    Project deleteProject(Long id) throws ExternalServiceException;
}
