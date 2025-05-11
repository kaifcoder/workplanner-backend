package com.example.servicemarketplace.service.impl;

import com.example.servicemarketplace.Dto.ServiceProviderDto;
import com.example.servicemarketplace.model.ServiceCategory;
import com.example.servicemarketplace.model.ServiceProvider;
import com.example.servicemarketplace.repository.ServiceCategoryRepository;
import com.example.servicemarketplace.repository.ServiceProviderRepository;
import com.example.servicemarketplace.service.ServiceProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceProviderServiceImpl implements ServiceProviderService {

  @Autowired
  private ServiceProviderRepository serviceProviderRepository;

  @Autowired
  private ServiceCategoryRepository serviceCategoryRepository;

  @Override
  public List<ServiceProvider> findAll() {
    return serviceProviderRepository.findAll();
  }

  @Override
  public ServiceProvider findById(Long id) {
    return serviceProviderRepository.findById(id).orElse(null);
  }

  @Override
  public ServiceProvider save(ServiceProviderDto dto) {
    // Convert IDs to ServiceCategory entities
    List<ServiceCategory> categories = dto.getCategoryIds().stream()
            .map(id -> serviceCategoryRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Category not found with id " + id)))
            .collect(Collectors.toList());

    // Build the provider entity
    ServiceProvider provider = new ServiceProvider();
    provider.setName(dto.getName());
    provider.setLocation(dto.getLocation());
    provider.setContact(dto.getContact());
    provider.setServiceCategories(categories);

    return serviceProviderRepository.save(provider);
  }

  @Override
  public void delete(Long id) {
    serviceProviderRepository.deleteById(id);
  }
}
