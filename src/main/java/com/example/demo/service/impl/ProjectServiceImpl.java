package com.example.demo.service.impl;

import static com.example.demo.util.Utils.generatePermalink;

import com.example.demo.entity.Project;
import com.example.demo.exception.ProjectDeleteException;
import com.example.demo.exception.ExternalServiceException;
import com.example.demo.exception.ProjectNotFoundException;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.service.ExternalService;
import com.example.demo.service.ProjectService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository repository;

    private final ExternalService externalService;

    public ProjectServiceImpl(ProjectRepository repository, ExternalService externalService) {
        this.repository = repository;
        this.externalService = externalService;
    }

    @Override
    public List<Project> getAll() {
        return repository.findAll();
    }

    @Override
    public Project getByPermalink(String permalink) throws ProjectNotFoundException {
        Project project = repository.findByPermalink(permalink);
        if (project == null) {
            throw new ProjectNotFoundException("Project [" + permalink + "] not found");
        }
        return project;
    }

    @Override
    @Transactional(rollbackFor = {ExternalServiceException.class})
    public Project create(Project project) throws ExternalServiceException {
        project.setPermalink(generatePermalink(project.getTitle()));
        Project newProject = repository.save(project);
        externalService.saveProject(newProject);
        return newProject;
    }

    @Override
    @Transactional(rollbackFor = {ExternalServiceException.class})
    public Project update(Project project) throws ExternalServiceException, ProjectNotFoundException {
        Project currentProject = getByPermalink(project.getPermalink());
        currentProject.setTitle(project.getTitle());
        currentProject.setDescription(project.getDescription());
        Project newProject = repository.save(currentProject);
        externalService.saveProject(newProject);
        return newProject;
    }

    @Override
    public Project delete(String permalink) throws ProjectNotFoundException, ProjectDeleteException {
        Project project = getByPermalink(permalink);
        try {
            repository.delete(project);
        } catch (DataIntegrityViolationException ex) {
            throw new ProjectDeleteException("We couldn't delete project [" + permalink + "]");
        }
        return project;
    }

    @Override
    public List<Project> getByAreasAndCapacitiesUsingQuery(List<String> areas, List<String> capacities) {
        return repository.findByAreasAndCapacities(areas, capacities);
    }

    @Override
    public Page<Project> getByAreasAndCapacitiesUsingQueryWithPagination(List<String> areas, List<String> capacities, Integer pageNumber) {
        return repository.findByAreasAndCapacitiesPageable(areas, capacities, PageRequest.of(pageNumber, 10));
    }

    @Override
    public List<Project> getByAreasAndCapacities(List<String> areas, List<String> capacities) {
        var areaNames = areas == null ? new ArrayList<String>() : areas;
        var capacityUnits = capacities == null ? new ArrayList<String>() : capacities;
        return repository.findDistinctByAreasNameInAndCapacitiesUnitIn(areaNames, capacityUnits);
    }

}
