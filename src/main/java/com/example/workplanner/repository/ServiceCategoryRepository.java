package com.example.workplanner.repository;

import com.example.workplanner.model.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory, Long> {
    ServiceCategory findByName(String name);
}
