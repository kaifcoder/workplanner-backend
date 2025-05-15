package com.example.workplanner.service.impl;

import com.example.workplanner.model.ServiceCategory;
import com.example.workplanner.repository.ServiceCategoryRepository;
import com.example.workplanner.service.ServiceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceCategoryServiceImpl implements ServiceCategoryService {

  @Autowired
  private ServiceCategoryRepository ServiceCategoryRepository;

  @Override
  public List<ServiceCategory> findAll() {
    return ServiceCategoryRepository.findAll();
  }

  @Override
  public ServiceCategory findById(Long id) {
    Optional<ServiceCategory> entity = ServiceCategoryRepository.findById(id);
    return entity.orElse(null);
  }

  @Override
  public ServiceCategory save(ServiceCategory entity) {
    return ServiceCategoryRepository.save(entity);
  }

  @Override
  public void delete(Long id) {
    ServiceCategoryRepository.deleteById(id);
  }
}
