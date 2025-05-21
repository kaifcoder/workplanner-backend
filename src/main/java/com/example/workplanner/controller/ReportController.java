package com.example.workplanner.controller;

import com.example.workplanner.Dto.TaskDto;
import com.example.workplanner.model.TaskStatus;
import com.example.workplanner.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/report")
public class ReportController {
    @Autowired
    private TaskService taskService;

    // Manager report: filter by status, team member, project (all optional)
    @GetMapping("/manager/tasks")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<TaskDto>> getManagerReport(
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) Long teamMemberId,
            @RequestParam(required = false) Long projectId
    ) {
        return ResponseEntity.ok(taskService.getTasksByFilters(status, teamMemberId, projectId));
    }

    // Team member report: only their own tasks, filter by status and project
    @GetMapping("/member/tasks")
    @PreAuthorize("hasRole('TEAM_MEMBER')")
    public ResponseEntity<List<TaskDto>> getTeamMemberReport(
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) Long projectId
    ) {
        return ResponseEntity.ok(taskService.getTasksForCurrentUserWithFilters(status, projectId));
    }
}
