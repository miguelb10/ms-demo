package com.example.demo.service.impl;

import com.example.demo.entity.Project;
import com.example.demo.exception.ExternalServiceException;
import com.example.demo.exception.ProjectNotFoundException;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.service.ExternalService;
import com.example.demo.service.ProjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
public class ProjectServiceImpl implements ProjectService {

    private static final Random random = new Random();

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
    public Project createProject(Project project) throws ExternalServiceException {
        project.setPermalink(generatePermalink(project.getTitle()));
        Project newProject = repository.save(project);
        externalService.saveProject(newProject);
        return newProject;
    }

    @Override
    @Transactional(rollbackFor = {ExternalServiceException.class})
    public Project updateProject(Project project) throws ExternalServiceException, ProjectNotFoundException {
        Project currentProject = getByPermalink(project.getPermalink());
        currentProject.setTitle(project.getTitle());
        currentProject.setDescription(project.getDescription());
        Project newProject = repository.save(currentProject);
        externalService.saveProject(newProject);
        return newProject;
    }

    @Override
    public Project deleteProject(Long id) throws ExternalServiceException {
        return null;
    }

    @Override
    public List<Project> getByAreas(List<String> areas) {
        return repository.findByAreaNames(areas);
    }

    private String generatePermalink(String title) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        StringBuilder sb = new StringBuilder(title.length());

        for (int i = 0; i < title.length(); i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString().concat("-").concat(title);
    }
}
