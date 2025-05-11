package com.example.servicemarketplace.service;

import com.example.servicemarketplace.model.ServiceEntity;

import java.util.List;

public interface ServiceEntityService {
    ServiceEntity createService(ServiceEntity serviceEntity);
    List<ServiceEntity> getAllServices();
    ServiceEntity getServiceById(Long id);
    void deleteService(Long id);
}