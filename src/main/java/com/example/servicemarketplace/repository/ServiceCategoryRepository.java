package com.example.servicemarketplace.repository;

import com.example.servicemarketplace.model.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory, Long> {
    ServiceCategory findByName(String name);
}
