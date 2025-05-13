package com.example.servicemarketplace.Dto;

import lombok.Data;

@Data
public class FeedbackCreateDTO {
    private String comment;
    private int rating;
    private Long serviceId;
    private Long userId;
}
