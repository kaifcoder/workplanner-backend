package com.example.servicemarketplace.repository;

import com.example.servicemarketplace.model.ServiceOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceOrderRepo extends JpaRepository<ServiceOrder, Long> {
}
