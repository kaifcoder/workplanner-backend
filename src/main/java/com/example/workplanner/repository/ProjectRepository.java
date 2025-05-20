package com.example.workplanner.repository;


import com.example.workplanner.model.Project;
import com.example.workplanner.model.Users;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByCreatedBy(Users user);
    Optional<Project> findByIdAndMembers_Id(Long projectId, Long userId);
    List<Project> findByMembers_Id(Long userId);
}