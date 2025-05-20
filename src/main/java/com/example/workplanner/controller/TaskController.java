package com.example.workplanner.controller;

import com.example.workplanner.Dto.TaskDto;
import com.example.workplanner.model.Task;
import com.example.workplanner.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        // Logic to create a task
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id) {
        // Logic to get a task by ID
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    // Team member suggests a task
    @PostMapping("/suggest/{projectId}")
    @PreAuthorize("hasRole('TEAM_MEMBER')")
    public ResponseEntity<TaskDto> suggestTask(@PathVariable Long projectId, @RequestBody TaskDto dto) {
        return ResponseEntity.ok(taskService.suggestTask(projectId, dto));
    }

    // Manager approves a suggested task
    @PostMapping("/approve/{taskId}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<TaskDto> approveTask(@PathVariable Long taskId) {
        return ResponseEntity.ok(taskService.approveTask(taskId));
    }

    // Manager rejects a suggested task
    @PostMapping("/reject/{taskId}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<TaskDto> rejectTask(@PathVariable Long taskId) {
        return ResponseEntity.ok(taskService.rejectTask(taskId));
    }

    // Manager assigns a task to a user
    @PostMapping("/assign/{taskId}/{userId}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<TaskDto> assignTask(@PathVariable Long taskId, @PathVariable Long userId) {
        return ResponseEntity.ok(taskService.assignTask(taskId, userId));
    }

    // List all tasks for a project
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<TaskDto>> getTasksForProject(@PathVariable Long projectId) {
        return ResponseEntity.ok(taskService.getTasksForProject(projectId));
    }

    // List all tasks assigned to the current user
    @GetMapping("/assigned")
    @PreAuthorize("hasRole('TEAM_MEMBER') or hasRole('MANAGER')")
    public ResponseEntity<List<TaskDto>> getAssignedTasksForCurrentUser() {
        return ResponseEntity.ok(taskService.getAssignedTasksForCurrentUser());
    }
}
