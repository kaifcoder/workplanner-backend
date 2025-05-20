package com.example.workplanner.service;

import com.example.workplanner.Dto.ProjectDto;
import com.example.workplanner.Dto.UserDto;
import com.example.workplanner.model.Project;
import com.example.workplanner.model.Users;
import com.example.workplanner.repository.ProjectRepository;
import com.example.workplanner.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ProjectServiceTest {
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private ProjectService projectService;

    private Users mockUser;
    private Project mockProject;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockUser = new Users();
        mockUser.setId(1L);
        mockUser.setUsername("manager1");
        mockUser.setRole("MANAGER");
        mockProject = new Project();
        mockProject.setId(1L);
        mockProject.setName("Project Alpha");
        mockProject.setCreatedBy(mockUser);
        SecurityContextHolder.clearContext();
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(mockUser));
    }

    @Test
    void createProject_shouldReturnProjectDto() {
        ProjectDto dto = new ProjectDto();
        dto.setName("Project Alpha");
        dto.setDescription("desc");
        // Mock SecurityContextHolder to return mockUser as the authenticated user
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn(mockUser.getUsername());
        SecurityContextHolder.getContext().setAuthentication(auth);
        when(projectRepository.save(any(Project.class))).thenAnswer(inv -> {
            Project p = inv.getArgument(0);
            p.setId(1L);
            p.setCreatedBy(mockUser); // Ensure createdBy is set for DTO mapping
            return p;
        });
        ProjectDto result = projectService.createProject(dto);
        assertEquals("Project Alpha", result.getName());
        assertNotNull(result.getCreatedBy());
    }

    @Test
    void addMember_shouldReturnProjectDtoWithMember() {
        Users member = new Users();
        member.setId(2L);
        member.setUsername("member1");
        member.setRole("TEAM_MEMBER");
        mockProject.setMembers(new HashSet<>());
        when(projectRepository.findById(1L)).thenReturn(Optional.of(mockProject));
        when(userRepository.findById(2L)).thenReturn(Optional.of(member));
        when(projectRepository.save(any(Project.class))).thenAnswer(inv -> inv.getArgument(0));
        ProjectDto result = projectService.addMember(1L, 2L);
        assertTrue(result.getMemberDtos().stream().anyMatch(u -> u.getId().equals(2L)));
    }

    @Test
    void getProjectsForCurrentUser_shouldReturnList() {
        // Mock SecurityContextHolder to return mockUser as the authenticated user
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn(mockUser.getUsername());
        SecurityContextHolder.getContext().setAuthentication(auth);
        mockProject.setMembers(Set.of(mockUser));
        when(projectRepository.findByMembers_Id(1L)).thenReturn(List.of(mockProject));
        List<ProjectDto> result = projectService.getProjectsForCurrentUser();
        assertEquals(1, result.size());
        assertEquals("Project Alpha", result.get(0).getName());
    }

    @Test
    void getProject_shouldReturnProjectDto() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(mockProject));
        ProjectDto result = projectService.getProject(1L);
        assertEquals("Project Alpha", result.getName());
    }
}
