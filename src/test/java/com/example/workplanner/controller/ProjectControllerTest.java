package com.example.workplanner.controller;

import com.example.workplanner.Dto.ProjectDto;
import com.example.workplanner.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProjectControllerTest {
    @Mock
    private ProjectService projectService;
    @InjectMocks
    private ProjectController projectController;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(projectController).build();
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    void createProject_shouldReturnProjectDto() throws Exception {
        ProjectDto dto = new ProjectDto();
        dto.setId(1L);
        dto.setName("Project Alpha");
        when(projectService.createProject(any(ProjectDto.class))).thenReturn(dto);
        mockMvc.perform(post("/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Project Alpha\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Project Alpha"));
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    void addMember_shouldReturnProjectDtoWithMember() throws Exception {
        ProjectDto dto = new ProjectDto();
        dto.setId(1L);
        dto.setName("Project Alpha");
        when(projectService.addMember(anyLong(), anyLong())).thenReturn(dto);
        mockMvc.perform(post("/projects/1/members?userId=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Project Alpha"));
    }

    @Test
    @WithMockUser
    void getProjectsForCurrentUser_shouldReturnList() throws Exception {
        ProjectDto dto = new ProjectDto();
        dto.setId(1L);
        dto.setName("Project Alpha");
        when(projectService.getProjectsForCurrentUser()).thenReturn(List.of(dto));
        mockMvc.perform(get("/projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Project Alpha"));
    }

    @Test
    @WithMockUser
    void getProject_shouldReturnProjectDto() throws Exception {
        ProjectDto dto = new ProjectDto();
        dto.setId(1L);
        dto.setName("Project Alpha");
        when(projectService.getProject(anyLong())).thenReturn(dto);
        mockMvc.perform(get("/projects/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Project Alpha"));
    }
}
