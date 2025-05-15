package com.example.workplanner.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public enum TaskStatus {
    PENDING,
    APPROVED,
    REJECTED,
    IN_PROGRESS,
    COMPLETED
}
