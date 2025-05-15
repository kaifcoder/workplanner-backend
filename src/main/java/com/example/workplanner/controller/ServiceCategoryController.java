package com.example.workplanner.controller;

import com.example.workplanner.model.ServiceCategory;
import com.example.workplanner.service.ServiceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/servicecategory")
public class ServiceCategoryController {

  @Autowired
  private ServiceCategoryService ServiceCategoryService;

  @GetMapping
  public List<ServiceCategory> getAll() {
    return ServiceCategoryService.findAll();
  }

  @GetMapping("/{id}")
  public ServiceCategory getById(@PathVariable Long id) {
    return ServiceCategoryService.findById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ServiceCategory create(@RequestBody ServiceCategory entity) {
    return ServiceCategoryService.save(entity);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    ServiceCategoryService.delete(id);
  }
}
