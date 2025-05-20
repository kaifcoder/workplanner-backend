package com.example.workplanner.Dto;

import lombok.Data;
import java.util.Date;
import java.util.Set;

@Data
public class ProjectDto {
    private Long id;
    private String name;
    private String description;
    private Long createdById;
    private Date endDate;
    private Set<Long> memberIds;
    private Set<Long> taskIds;
}
