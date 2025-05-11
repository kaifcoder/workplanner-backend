package com.example.servicemarketplace.repository;


import com.example.servicemarketplace.model.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceEntityRepository extends JpaRepository<ServiceEntity, Long> {
    // You can add custom queries if needed later
}

