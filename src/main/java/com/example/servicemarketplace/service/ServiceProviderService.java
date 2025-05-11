package com.example.servicemarketplace.service;

import com.example.servicemarketplace.Dto.ServiceProviderDto;
import com.example.servicemarketplace.model.ServiceProvider;
import java.util.List;

public interface ServiceProviderService {
  List<ServiceProvider> findAll();
  ServiceProvider findById(Long id);
  ServiceProvider save(ServiceProviderDto entity);
  void delete(Long id);
}
