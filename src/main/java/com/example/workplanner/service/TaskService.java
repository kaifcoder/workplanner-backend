package com.example.workplanner.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.workplanner.Dto.ProjectDto;
import com.example.workplanner.Dto.TaskDto;
import com.example.workplanner.Dto.UserDto;
import com.example.workplanner.model.Project;
import com.example.workplanner.model.Task;
import com.example.workplanner.model.TaskStatus;
import com.example.workplanner.model.Users;
import com.example.workplanner.repository.ProjectRepository;
import com.example.workplanner.repository.TaskRepository;
import com.example.workplanner.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private EmailService emailService;


    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    // Manager creates a new task for a project and assigns to a user (optional)
    public TaskDto createTask(TaskDto dto) {
        Project project = projectRepository.findById(dto.getProjectId()).orElseThrow();
        Users assignedTo = null;
        if (dto.getAssignedToUser() != null && dto.getAssignedToUser().getId() != null) {
            assignedTo = userRepository.findById(dto.getAssignedToUser().getId()).orElse(null);
        }
        Task task = Task.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .dueDate(dto.getDueDate())
                .createdDate(new java.util.Date())
                .status(TaskStatus.ASSIGNED)
                .project(project)
                .assignedTo(assignedTo)
                .build();
        log.info("Creating task: {}", task.getTitle());
        log.info("Assigned to: {}", assignedTo != null ? assignedTo.getUsername() : "No user assigned");
        // Save the task
        log.info("Saving task to repository");
        log.info("Project: {}", project.getName());

        Task savedTask = taskRepository.save(task);
        // Optionally send email notification if assignedTo is present
        if (assignedTo != null && assignedTo.getUsername() != null && assignedTo.getUsername().contains("@")) {
            emailService.sendEmail(
                assignedTo.getUsername(),
                "Task Assigned: " + task.getTitle(),
                "You have been assigned a new task: " + task.getTitle() + "\nDescription: " + task.getDescription()
            );
        }
        return toDto(savedTask);
    }

    public TaskDto updateTask(Long id, TaskDto dto) {
        Task task = taskRepository.findById(id).orElseThrow();
        if (dto.getTitle() != null) task.setTitle(dto.getTitle());
        if (dto.getDescription() != null) task.setDescription(dto.getDescription());
        if (dto.getDueDate() != null) task.setDueDate(dto.getDueDate());
        if (dto.getStatus() != null) task.setStatus(TaskStatus.valueOf(dto.getStatus()));
        if (dto.getAssignedToUser() != null && dto.getAssignedToUser().getId() != null) {
            Users assignedTo = userRepository.findById(dto.getAssignedToUser().getId()).orElse(null);
            task.setAssignedTo(assignedTo);
        }
        if (dto.getProjectId() != null) {
            Project project = projectRepository.findById(dto.getProjectId()).orElse(null);
            task.setProject(project);
        }
        Task savedTask = taskRepository.save(task);
        return toDto(savedTask);
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
        Task savedTask = taskRepository.save(task);
        // Send email notification
        if (user.getUsername() != null && user.getUsername().contains("@")) { // crude email check
            emailService.sendEmail(
                user.getUsername(),
                "Task Assigned: " + task.getTitle(),
                "You have been assigned a new task: " + task.getTitle() + "\nDescription: " + task.getDescription()
            );
        }
        return toDto(savedTask);
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
            dto.setAssignedToId(task.getAssignedTo().getId());
        }
        if (task.getSuggestedBy() != null) {
            UserDto userDto = new UserDto();
            userDto.setId(task.getSuggestedBy().getId());
            userDto.setUsername(task.getSuggestedBy().getUsername());
            userDto.setRole(task.getSuggestedBy().getRole());
            dto.setSuggestedByUser(userDto);
            dto.setSuggestedById(task.getSuggestedBy().getId());
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
            dto.setProjectId(project.getId());
        }
        return dto;
    }
}
