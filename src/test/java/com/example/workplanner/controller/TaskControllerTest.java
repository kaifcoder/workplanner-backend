package com.example.workplanner.controller;

import com.example.workplanner.Dto.TaskDto;
import com.example.workplanner.model.TaskStatus;
import com.example.workplanner.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import org.springframework.security.test.context.support.WithMockUser;

class TaskControllerTest {
    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser(roles = "TEAM_MEMBER")
    void suggestTask_shouldReturnTaskDto() {
        TaskDto dto = new TaskDto();
        dto.setTitle("Test Task");
        dto.setStatus(TaskStatus.SUGGESTED.name());
        when(taskService.suggestTask(anyLong(), any(TaskDto.class))).thenReturn(dto);
        ResponseEntity<TaskDto> response = taskController.suggestTask(1L, dto);
        assertNotNull(response.getBody());
        TaskDto responseBody = response.getBody();
        assertEquals("Test Task", responseBody.getTitle());
        assertEquals(TaskStatus.SUGGESTED.name(), responseBody.getStatus());
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    void approveTask_shouldReturnTaskDto() {
        TaskDto dto = new TaskDto();
        dto.setStatus(TaskStatus.APPROVED.name());
        when(taskService.approveTask(anyLong())).thenReturn(dto);
        ResponseEntity<TaskDto> response = taskController.approveTask(1L);
        assertEquals(TaskStatus.APPROVED.name(), response.getBody().getStatus());
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    void rejectTask_shouldReturnTaskDto() {
        TaskDto dto = new TaskDto();
        dto.setStatus(TaskStatus.REJECTED.name());
        when(taskService.rejectTask(anyLong())).thenReturn(dto);
        ResponseEntity<TaskDto> response = taskController.rejectTask(1L);
        assertEquals(TaskStatus.REJECTED.name(), response.getBody().getStatus());
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    void assignTask_shouldReturnTaskDto() {
        TaskDto dto = new TaskDto();
        dto.setStatus(TaskStatus.ASSIGNED.name());
        when(taskService.assignTask(anyLong(), anyLong())).thenReturn(dto);
        ResponseEntity<TaskDto> response = taskController.assignTask(1L, 2L);
        assertEquals(TaskStatus.ASSIGNED.name(), response.getBody().getStatus());
    }

    @Test
    void getTasksForProject_shouldReturnListOfTaskDto() {
        TaskDto dto1 = new TaskDto();
        dto1.setId(1L);
        TaskDto dto2 = new TaskDto();
        dto2.setId(2L);
        List<TaskDto> list = Arrays.asList(dto1, dto2);
        when(taskService.getTasksForProject(anyLong())).thenReturn(list);
        ResponseEntity<List<TaskDto>> response = taskController.getTasksForProject(1L);
        assertEquals(2, response.getBody().size());
    }

    @Test
    @WithMockUser(roles = {"TEAM_MEMBER", "MANAGER"})
    void getAssignedTasksForCurrentUser_shouldReturnListOfTaskDto() {
        TaskDto dto = new TaskDto();
        dto.setId(1L);
        when(taskService.getAssignedTasksForCurrentUser()).thenReturn(Arrays.asList(dto));
        ResponseEntity<List<TaskDto>> response = taskController.getAssignedTasksForCurrentUser();
        assertEquals(1, response.getBody().size());
    }
}
