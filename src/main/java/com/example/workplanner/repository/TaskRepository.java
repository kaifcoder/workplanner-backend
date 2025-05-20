package com.example.workplanner.repository;


import com.example.workplanner.model.Task;
import com.example.workplanner.model.TaskStatus;
import com.example.workplanner.model.Users;
import com.example.workplanner.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByProject(Project project);
    List<Task> findByAssignedTo(Users user);
    List<Task> findByCreatedDate(Date date);
    List<Task> findByStatus(TaskStatus status);

    default List<Task> findByProjectId(Long projectId) {
        Project p = new Project();
        p.setId(projectId);
        return findByProject(p);
    }
}

