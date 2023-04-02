package com.example.demo.service;

import com.example.demo.entity.Project;
import com.example.demo.exception.ProjectDeleteException;
import com.example.demo.exception.ExternalServiceException;
import com.example.demo.exception.ProjectNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProjectService {

    List<Project> getAll();

    Project getByPermalink(String permalink) throws ProjectNotFoundException;

    Project create(Project project) throws ExternalServiceException;

    Project update(Project project) throws ExternalServiceException, ProjectNotFoundException;

    Project delete(String permalink) throws ProjectNotFoundException, ProjectDeleteException;

    List<Project> getByAreasAndCapacitiesUsingQuery(List<String> areas, List<String> capacities);

    Page<Project> getByAreasAndCapacitiesUsingQueryWithPagination(List<String> areas, List<String> capacities, Integer pageNumber);

    List<Project> getByAreasAndCapacities(List<String> areas, List<String> capacities);
}
