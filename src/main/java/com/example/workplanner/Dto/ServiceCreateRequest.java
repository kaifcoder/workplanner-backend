package com.example.workplanner.Dto;

import lombok.Data;

@Data
public class ServiceCreateRequest {
    private String name;
    private String description;
    private Double cost;
    private String location;
    private String providerName;
    private String contactInfo;
    private Long categoryId;
}

