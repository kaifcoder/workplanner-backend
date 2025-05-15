package com.example.workplanner.service;

import com.example.workplanner.model.ServiceCategory;
import java.util.List;

public interface ServiceCategoryService {
  List<ServiceCategory> findAll();
  ServiceCategory findById(Long id);
  ServiceCategory save(ServiceCategory entity);
  void delete(Long id);
}
