package com.example.demo.controller;

import com.example.demo.controller.dto.ProjectFilterRequestDto;
import com.example.demo.controller.dto.ProjectRequestDto;
import com.example.demo.entity.Project;
import com.example.demo.exception.ProjectDeleteException;
import com.example.demo.exception.ExternalServiceException;
import com.example.demo.exception.ProjectNotFoundException;
import com.example.demo.service.ProjectService;
import com.example.demo.util.EntityUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@RestController
@RequestMapping("/v1/project")
@Validated
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/{permalink}")
    public Project get(@PathVariable @NotBlank @Size(min = 5, max = 99) String permalink) throws ProjectNotFoundException {
        return projectService.getByPermalink(permalink);
    }

    @PostMapping("/create")
    public Project create(@Valid @RequestBody ProjectRequestDto request) throws ExternalServiceException {
        return projectService.create(EntityUtilities.copyObjectFrom(request, Project.class));
    }

    @DeleteMapping("/{permalink}")
    public Project delete(@PathVariable @NotBlank @Size(min = 5, max = 99) String permalink) throws ProjectNotFoundException, ProjectDeleteException {
        return projectService.delete(permalink);
    }

    @PutMapping("/{permalink}")
    public Project edit(@Valid @RequestBody ProjectRequestDto request, @PathVariable @NotBlank @Size(min = 5, max = 99) String permalink) throws ExternalServiceException, ProjectNotFoundException {
        Project project = EntityUtilities.copyObjectFrom(request, Project.class);
        project.setPermalink(permalink);
        return projectService.update(project);
    }

    @GetMapping("/list-using-query")
    public List<Project> listUsingQuery(@Valid @ModelAttribute ProjectFilterRequestDto request) {
        return projectService.getByAreasAndCapacitiesUsingQuery(request.getAreas(), request.getCapacities());
    }

    @GetMapping("/list-using-query/{pageNumber}")
    public Page<Project> listUsingQueryWithPagination(@Valid @ModelAttribute ProjectFilterRequestDto request, @PathVariable Integer pageNumber) {
        return projectService.getByAreasAndCapacitiesUsingQueryWithPagination(request.getAreas(), request.getCapacities(), pageNumber);
    }

    @GetMapping("/list")
    public List<Project> listWithoutQuery(@Valid @ModelAttribute ProjectFilterRequestDto request) {
        return projectService.getByAreasAndCapacities(request.getAreas(), request.getCapacities());
    }
}
