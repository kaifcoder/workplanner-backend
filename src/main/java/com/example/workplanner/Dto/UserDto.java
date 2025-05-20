package com.example.workplanner.Dto;

import lombok.Data;
import java.util.Set;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String role;
    private Set<Long> projectIds;
    private Set<Long> assignedTaskIds;
    private Set<Long> suggestedTaskIds;
}
