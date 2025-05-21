package com.example.workplanner.service;

import com.example.workplanner.Dto.TaskDto;
import com.example.workplanner.Dto.UserDto;
import com.example.workplanner.Dto.ProjectDto;
import com.example.workplanner.model.Task;
import com.example.workplanner.model.TaskStatus;
import com.example.workplanner.model.Users;
import com.example.workplanner.model.Project;
import com.example.workplanner.repository.TaskRepository;
import com.example.workplanner.repository.UserRepository;
import com.example.workplanner.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;


    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public Task createTask(Task task) {
        // Logic to create a task
//        Users u = customUserDetailService.getCurrentUser();
//        System.out.println("Current user: " + u.getUsername() + " " + u.getId() + " " + u.getRole());
        return taskRepository.save(task);
    }

    private Users getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(auth.getName()).orElseThrow();
    }

    public TaskDto suggestTask(Long projectId, TaskDto dto) {
        Users currentUser = getCurrentUser();
        Project project = projectRepository.findById(projectId).orElseThrow();
        Task task = Task.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .dueDate(dto.getDueDate())
                .createdDate(new java.util.Date())
                .status(TaskStatus.SUGGESTED)
                .suggestedBy(currentUser)
                .project(project)
                .build();
        return toDto(taskRepository.save(task));
    }

    public TaskDto approveTask(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow();
        task.setStatus(TaskStatus.APPROVED);
        return toDto(taskRepository.save(task));
    }

    public TaskDto rejectTask(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow();
        task.setStatus(TaskStatus.REJECTED);
        return toDto(taskRepository.save(task));
    }

    public TaskDto assignTask(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId).orElseThrow();
        Users user = userRepository.findById(userId).orElseThrow();
        task.setAssignedTo(user);
        task.setStatus(TaskStatus.ASSIGNED);
        return toDto(taskRepository.save(task));
    }

    public List<TaskDto> getTasksForProject(Long projectId) {
        return taskRepository.findByProjectId(projectId)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<TaskDto> getAssignedTasksForCurrentUser() {
        Users currentUser = getCurrentUser();
        return taskRepository.findByAssignedTo(currentUser)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    // For manager: all filters optional
    public List<TaskDto> getTasksByFilters(TaskStatus status, Long teamMemberId, Long projectId) {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .filter(t -> status == null || t.getStatus() == status)
                .filter(t -> teamMemberId == null || (t.getAssignedTo() != null && t.getAssignedTo().getId().equals(teamMemberId)))
                .filter(t -> projectId == null || (t.getProject() != null && t.getProject().getId().equals(projectId)))
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // For team member: only their own tasks, filter by status and project
    public List<TaskDto> getTasksForCurrentUserWithFilters(TaskStatus status, Long projectId) {
        Users currentUser = getCurrentUser();
        List<Task> tasks = taskRepository.findByAssignedTo(currentUser);
        return tasks.stream()
                .filter(t -> status == null || t.getStatus() == status)
                .filter(t -> projectId == null || (t.getProject() != null && t.getProject().getId().equals(projectId)))
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private TaskDto toDto(Task task) {
        TaskDto dto = new TaskDto();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setDueDate(task.getDueDate());
        dto.setCreatedDate(task.getCreatedDate());
        dto.setStatus(task.getStatus().name());
        if (task.getAssignedTo() != null) {
            UserDto userDto = new UserDto();
            userDto.setId(task.getAssignedTo().getId());
            userDto.setUsername(task.getAssignedTo().getUsername());
            userDto.setRole(task.getAssignedTo().getRole());
            dto.setAssignedToUser(userDto);
        }
        if (task.getSuggestedBy() != null) {
            UserDto userDto = new UserDto();
            userDto.setId(task.getSuggestedBy().getId());
            userDto.setUsername(task.getSuggestedBy().getUsername());
            userDto.setRole(task.getSuggestedBy().getRole());
            dto.setSuggestedByUser(userDto);
        }
        if (task.getProject() != null) {
            Project project = task.getProject();
            ProjectDto projectDto = new ProjectDto();
            projectDto.setId(project.getId());
            projectDto.setName(project.getName());
            projectDto.setDescription(project.getDescription());
            projectDto.setEndDate(project.getEndDate());
            if (project.getCreatedBy() != null) {
                UserDto createdByDto = new UserDto();
                createdByDto.setId(project.getCreatedBy().getId());
                createdByDto.setUsername(project.getCreatedBy().getUsername());
                createdByDto.setRole(project.getCreatedBy().getRole());
                projectDto.setCreatedBy(createdByDto);
            }
            dto.setProject(projectDto);
        }
        return dto;
    }
}
