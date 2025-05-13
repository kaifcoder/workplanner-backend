package com.example.servicemarketplace.controller;

import com.example.servicemarketplace.Dto.ServiceCreateRequest;
import com.example.servicemarketplace.model.ServiceEntity;
import com.example.servicemarketplace.service.ServiceEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServiceEntityController {

    @Autowired
    private ServiceEntityService serviceEntityService;

    @PostMapping
    public ResponseEntity<ServiceEntity> createService(@RequestBody ServiceCreateRequest serviceEntity) {
        return ResponseEntity.ok(serviceEntityService.createService(serviceEntity));
    }

    @GetMapping
    public ResponseEntity<List<ServiceEntity>> getAllServices(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Double minCost,
            @RequestParam(required = false) Double maxCost,
            @RequestParam(required = false) String location
    ) {
        return ResponseEntity.ok(serviceEntityService.getFilteredServices(categoryId, minCost, maxCost, location));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceEntity> getServiceById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceEntityService.getServiceById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        serviceEntityService.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}

