package com.example.workplanner.service;

import com.example.workplanner.model.Project;
import com.example.workplanner.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    // Create a new project
    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    // Retrieve all projects
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
}
