package com.example.servicemarketplace.Dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ServiceCreateRequest {
    private String name;
    private String description;
    private BigDecimal cost;
    private String location;
    private String providerName;
    private String contactInfo;
    private String categoryName;
}

