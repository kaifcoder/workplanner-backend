package com.example.workplanner.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.workplanner.Dto.ProjectDto;
import com.example.workplanner.Dto.UserDto;
import com.example.workplanner.service.ProjectService;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    // Create a new project (Manager only)
    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ProjectDto createProject(@RequestBody ProjectDto projectDto) {
        return projectService.createProject(projectDto);
    }

    // Add a member to a project (Manager only)
    @PostMapping("/{projectId}/members")
    @PreAuthorize("hasRole('MANAGER')")
    public ProjectDto addMember(@PathVariable Long projectId, @RequestParam Long userId) {
        return projectService.addMember(projectId, userId);
    }

    // View all projects for current user
    @GetMapping
    public List<ProjectDto> getProjectsForCurrentUser() {
        return projectService.getProjectsForCurrentUser();
    }

    // View a single project by id
    @GetMapping("/{id}")
    public ProjectDto getProject(@PathVariable Long id) {
        return projectService.getProject(id);
    }

    // View all team members
    @GetMapping("/team-members")
    public List<UserDto> getAllTeamMembers() {
        return projectService.getAllTeamMembers();
    }

    // Get all projects (Manager only)
    @GetMapping("/all")
    @PreAuthorize("hasRole('MANAGER') or hasRole('TEAM_MEMBER')")
    public List<ProjectDto> getAllProjects() {
        return projectService.getAllProjects();
    }

    // Delete a project (Manager only)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    // Update a project (Manager only)
    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ProjectDto updateProject(@PathVariable Long id, @RequestBody ProjectDto projectDto) {
        return projectService.updateProject(id, projectDto);
    }
}

