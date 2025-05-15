package com.example.workplanner.service;

import com.example.workplanner.Dto.ServiceCreateRequest;
import com.example.workplanner.model.ServiceEntity;
import java.util.List;

public interface ServiceEntityService {
    ServiceEntity createService(ServiceCreateRequest serviceEntity);
    List<ServiceEntity> getAllServices();
    ServiceEntity getServiceById(Long id);
    void deleteService(Long id);
    List<ServiceEntity> getFilteredServices(Long categoryId, Double minCost, Double maxCost, String location);
}