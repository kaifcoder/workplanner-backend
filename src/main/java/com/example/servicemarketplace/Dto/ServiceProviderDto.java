package com.example.servicemarketplace.Dto;

import lombok.Data;

import java.util.List;

@Data
public class ServiceProviderDto {
    private String name;
    private String location;
    private String contact;
    private List<Long> categoryIds;
}

