package com.example.servicemarketplace.service.impl;

import com.example.servicemarketplace.model.ServiceEntity;
import com.example.servicemarketplace.repository.ServiceEntityRepository;
import com.example.servicemarketplace.service.ServiceEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceEntityServiceImpl implements ServiceEntityService {

    @Autowired
    private ServiceEntityRepository serviceEntityRepository;

    @Override
    public ServiceEntity createService(ServiceEntity serviceEntity) {
        return serviceEntityRepository.save(serviceEntity);
    }

    @Override
    public List<ServiceEntity> getAllServices() {
        return serviceEntityRepository.findAll();
    }

    @Override
    public ServiceEntity getServiceById(Long id) {
        return serviceEntityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found with id: " + id));
    }

    @Override
    public void deleteService(Long id) {
        serviceEntityRepository.deleteById(id);
    }
}
