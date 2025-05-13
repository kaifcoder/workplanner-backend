package com.example.servicemarketplace.service;

import com.example.servicemarketplace.Dto.ServiceCreateRequest;
import com.example.servicemarketplace.model.ServiceEntity;
import java.util.List;

public interface ServiceEntityService {
    ServiceEntity createService(ServiceCreateRequest serviceEntity);
    List<ServiceEntity> getAllServices();
    ServiceEntity getServiceById(Long id);
    void deleteService(Long id);
    List<ServiceEntity> getFilteredServices(Long categoryId, Double minCost, Double maxCost, String location);
}