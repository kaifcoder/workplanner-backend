package com.example.servicemarketplace.controller;

import com.example.servicemarketplace.model.ServiceProvider;
import com.example.servicemarketplace.service.ServiceProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/serviceprovider")
public class ServiceProviderController {

  @Autowired
  private ServiceProviderService ServiceProviderService;

  @GetMapping
  public List<ServiceProvider> getAll() {
    return ServiceProviderService.findAll();
  }

  @GetMapping("/{id}")
  public ServiceProvider getById(@PathVariable Long id) {
    return ServiceProviderService.findById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ServiceProvider create(@RequestBody ServiceProvider entity) {
    return ServiceProviderService.save(entity);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    ServiceProviderService.delete(id);
  }
}
