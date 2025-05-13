package com.example.servicemarketplace.service.impl;

import com.example.servicemarketplace.Dto.ServiceCreateRequest;
import com.example.servicemarketplace.model.ServiceCategory;
import com.example.servicemarketplace.model.ServiceEntity;
import com.example.servicemarketplace.repository.ServiceCategoryRepository;
import com.example.servicemarketplace.repository.ServiceEntityRepository;
import com.example.servicemarketplace.service.ServiceCategoryService;
import com.example.servicemarketplace.service.ServiceEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceEntityServiceImpl implements ServiceEntityService {

    @Autowired
    private ServiceEntityRepository serviceEntityRepository;

    @Autowired
    private ServiceCategoryRepository serviceCategoryRepository;

    @Override
    public ServiceEntity createService(ServiceCreateRequest serviceEntity) {
        ServiceEntity newServiceEntity = new ServiceEntity();
        newServiceEntity.setName(serviceEntity.getName());
        newServiceEntity.setDescription(serviceEntity.getDescription());
        newServiceEntity.setPrice(serviceEntity.getCost());
        ServiceCategory serviceCategory = serviceCategoryRepository.findById(serviceEntity.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Service category not found with id: " + serviceEntity.getCategoryId()));
        newServiceEntity.setCategory(serviceCategory);
        newServiceEntity.setLocation(serviceEntity.getLocation());
        newServiceEntity.setProviderName(serviceEntity.getProviderName());
        newServiceEntity.setProviderPhone(serviceEntity.getContactInfo());
        return serviceEntityRepository.save(newServiceEntity);
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

    @Override
    public List<ServiceEntity> getFilteredServices(Long categoryId, Double minCost, Double maxCost, String location) {
        return serviceEntityRepository.findByFilters(categoryId, minCost, maxCost, location);
    }
}
