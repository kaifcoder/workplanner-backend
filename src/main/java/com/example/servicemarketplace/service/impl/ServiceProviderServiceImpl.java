package com.example.servicemarketplace.service.impl;

import com.example.servicemarketplace.model.ServiceProvider;
import com.example.servicemarketplace.repository.ServiceProviderRepository;
import com.example.servicemarketplace.service.ServiceProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceProviderServiceImpl implements ServiceProviderService {

  @Autowired
  private ServiceProviderRepository ServiceProviderRepository;

  @Override
  public List<ServiceProvider> findAll() {
    return ServiceProviderRepository.findAll();
  }

  @Override
  public ServiceProvider findById(Long id) {
    Optional<ServiceProvider> entity = ServiceProviderRepository.findById(id);
    return entity.orElse(null);
  }

  @Override
  public ServiceProvider save(ServiceProvider entity) {
    return ServiceProviderRepository.save(entity);
  }

  @Override
  public void delete(Long id) {
    ServiceProviderRepository.deleteById(id);
  }
}
