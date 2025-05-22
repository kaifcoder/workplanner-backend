package com.example.workplanner.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.workplanner.Dto.ProjectDto;
import com.example.workplanner.Dto.UserDto;
import com.example.workplanner.model.Project;
import com.example.workplanner.model.Users;
import com.example.workplanner.repository.ProjectRepository;
import com.example.workplanner.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    private Users getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(auth.getName()).orElseThrow();
    }

    public ProjectDto createProject(ProjectDto dto) {
        Users currentUser = getCurrentUser();
        log.info("Current user: {}", currentUser.getRole());
        log.info("Creating project: {}", dto.getName());
        Project project = Project.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .endDate(dto.getEndDate())
                .createdBy(currentUser)
                .build();
        Project saved = projectRepository.save(project);
        return toDto(saved);
    }

    public ProjectDto addMember(Long projectId, Long userId) {
        Project project = projectRepository.findById(projectId).orElseThrow();
        Users user = userRepository.findById(userId).orElseThrow();
        project.getMembers().add(user);
        Project saved = projectRepository.save(project);
        return toDto(saved);
    }

    public List<ProjectDto> getProjectsForCurrentUser() {
        Users currentUser = getCurrentUser();
        List<Project> projects = projectRepository.findByMembers_Id(currentUser.getId());
        return projects.stream().map(this::toDto).collect(Collectors.toList());
    }

    public ProjectDto getProject(Long id) {
        Project project = projectRepository.findById(id).orElseThrow();
        return toDto(project);
    }

    private ProjectDto toDto(Project project) {
        ProjectDto dto = new ProjectDto();
        dto.setId(project.getId());
        dto.setName(project.getName());
        dto.setDescription(project.getDescription());
        dto.setEndDate(project.getEndDate());
        if (project.getCreatedBy() != null) {
            UserDto userDto = new UserDto();
            userDto.setId(project.getCreatedBy().getId());
            userDto.setUsername(project.getCreatedBy().getUsername());
            userDto.setRole(project.getCreatedBy().getRole());
            dto.setCreatedBy(userDto);
        }
        if (project.getMembers() != null) {
            Set<UserDto> memberDtos = project.getMembers().stream().map(u -> {
                UserDto ud = new UserDto();
                ud.setId(u.getId());
                ud.setUsername(u.getUsername());
                ud.setRole(u.getRole());
                return ud;
            }).collect(Collectors.toSet());
            dto.setMemberDtos(memberDtos);
        }
        return dto;
    }
}