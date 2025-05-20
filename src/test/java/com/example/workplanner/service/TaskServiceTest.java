package com.example.workplanner.service;

import com.example.workplanner.Dto.TaskDto;
import com.example.workplanner.model.Project;
import com.example.workplanner.model.Task;
import com.example.workplanner.model.TaskStatus;
import com.example.workplanner.model.Users;
import com.example.workplanner.repository.ProjectRepository;
import com.example.workplanner.repository.TaskRepository;
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

class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private Authentication authentication;
    @InjectMocks
    private TaskService taskService;

    private Users mockUser;
    private Project mockProject;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockUser = new Users();
        mockUser.setId(1L);
        mockUser.setUsername("user1");
        mockUser.setRole("TEAM_MEMBER");
        mockProject = new Project();
        mockProject.setId(1L);
        mockProject.setName("Project1");
        SecurityContextHolder.clearContext();
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(mockUser));
    }

    @Test
    void suggestTask_shouldReturnSuggestedTaskDto() {
        TaskDto dto = new TaskDto();
        dto.setTitle("TaskTitle");
        dto.setDescription("desc");
        dto.setDueDate(new Date());
        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(mockProject));
        when(taskRepository.save(any(Task.class))).thenAnswer(inv -> inv.getArgument(0));
        TaskDto result = taskService.suggestTask(1L, dto);
        assertEquals("TaskTitle", result.getTitle());
        assertEquals(TaskStatus.SUGGESTED.name(), result.getStatus());
        assertNotNull(result.getProject());
        assertNotNull(result.getSuggestedByUser());
    }

    @Test
    void approveTask_shouldSetStatusApproved() {
        Task task = new Task();
        task.setId(1L);
        task.setStatus(TaskStatus.SUGGESTED);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenAnswer(inv -> inv.getArgument(0));
        TaskDto result = taskService.approveTask(1L);
        assertEquals(TaskStatus.APPROVED.name(), result.getStatus());
    }

    @Test
    void rejectTask_shouldSetStatusRejected() {
        Task task = new Task();
        task.setId(1L);
        task.setStatus(TaskStatus.SUGGESTED);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenAnswer(inv -> inv.getArgument(0));
        TaskDto result = taskService.rejectTask(1L);
        assertEquals(TaskStatus.REJECTED.name(), result.getStatus());
    }

    @Test
    void assignTask_shouldSetAssignedUserAndStatus() {
        Task task = new Task();
        task.setId(1L);
        task.setStatus(TaskStatus.APPROVED);
        Users user = new Users();
        user.setId(2L);
        user.setUsername("user2");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));
        when(taskRepository.save(any(Task.class))).thenAnswer(inv -> inv.getArgument(0));
        TaskDto result = taskService.assignTask(1L, 2L);
        assertEquals(TaskStatus.ASSIGNED.name(), result.getStatus());
        assertNotNull(result.getAssignedToUser());
        assertEquals("user2", result.getAssignedToUser().getUsername());
    }

    @Test
    void getTasksForProject_shouldReturnList() {
        Task task1 = new Task();
        task1.setId(1L);
        task1.setProject(mockProject);
        Task task2 = new Task();
        task2.setId(2L);
        task2.setProject(mockProject);
        when(taskRepository.findByProjectId(1L)).thenReturn(Arrays.asList(task1, task2));
        List<TaskDto> result = taskService.getTasksForProject(1L);
        assertEquals(2, result.size());
    }

    @Test
    void getAssignedTasksForCurrentUser_shouldReturnList() {
        Task task = new Task();
        task.setId(1L);
        task.setAssignedTo(mockUser);
        when(taskRepository.findByAssignedTo(mockUser)).thenReturn(Collections.singletonList(task));
        List<TaskDto> result = taskService.getAssignedTasksForCurrentUser();
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }
}
