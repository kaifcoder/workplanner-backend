package com.example.workplanner.repository;

import com.example.workplanner.model.ServiceOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceOrderRepo extends JpaRepository<ServiceOrder, Long> {
}
