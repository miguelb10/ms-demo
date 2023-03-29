package com.example.demo.controller;

import com.example.demo.controller.dto.ProjectFilterRequestDto;
import com.example.demo.controller.dto.ProjectRequestDto;
import com.example.demo.entity.Project;
import com.example.demo.exception.ExternalServiceException;
import com.example.demo.exception.ProjectNotFoundException;
import com.example.demo.service.ProjectService;
import com.example.demo.util.EntityUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@RestController
@RequestMapping("/v1/project")
@Validated
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/{permalink}")
    public Project get(@PathVariable("permalink") @NotBlank @Size(min = 5, max = 99) String permalink) throws ProjectNotFoundException {
        return projectService.getByPermalink(permalink);
    }

    @PostMapping("/create")
    public Project create(@Valid @RequestBody ProjectRequestDto request) throws ExternalServiceException {
        return projectService.createProject(EntityUtilities.copyObjectFrom(request, Project.class));
    }

    @PostMapping("/edit/{permalink}")
    public Project edit(@Valid @RequestBody ProjectRequestDto request, @PathVariable("permalink") @NotBlank @Size(min = 5, max = 99) String permalink) throws ExternalServiceException, ProjectNotFoundException {
        Project project = EntityUtilities.copyObjectFrom(request, Project.class);
        project.setPermalink(permalink);
        return projectService.updateProject(project);
    }

    @GetMapping("/list")
    public List<Project> list(@Valid @ModelAttribute ProjectFilterRequestDto request) {
        return projectService.getByAreas(request.getAreas());
    }
}
