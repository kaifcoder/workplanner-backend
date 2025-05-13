package com.example.servicemarketplace.repository;


import com.example.servicemarketplace.model.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceEntityRepository extends JpaRepository<ServiceEntity, Long> {
    // You can add custom queries if needed later
    @Query("SELECT s FROM ServiceEntity s WHERE "
            + "(:categoryId IS NULL OR s.category.id = :categoryId) AND "
            + "(:minCost IS NULL OR s.price >= :minCost) AND "
            + "(:maxCost IS NULL OR s.price <= :maxCost) AND "
            + "(:location IS NULL OR s.location = :location)")
    List<ServiceEntity> findByFilters(Long categoryId, Double minCost, Double maxCost, String location);
}

