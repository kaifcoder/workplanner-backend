package com.example.workplanner.service;

import com.example.workplanner.model.Task;
import com.example.workplanner.model.Users;
import com.example.workplanner.repository.TaskRepository;
import com.example.workplanner.security.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public Task createTask(Task task) {
        // Logic to create a task
        Users u = customUserDetailService.getCurrentUser();
        System.out.println("Current user: " + u.getUsername() + " " + u.getId() + " " + u.getRole());
        return taskRepository.save(task);
    }
}
