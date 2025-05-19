package com.example.workplanner.controller;
import com.example.workplanner.model.Project;
import com.example.workplanner.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    // Create a new project
    @PostMapping
    public Project createProject(@RequestBody Project project) {
        return projectService.createProject(project);
    }

    // View all projects
    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }
}

