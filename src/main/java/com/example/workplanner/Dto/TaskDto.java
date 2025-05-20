package com.example.workplanner.Dto;

import lombok.Data;
import java.util.Date;

@Data
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private Date dueDate;
    private Date createdDate;
    private String status;
    private Long assignedToId;
    private Long suggestedById;
    private Long projectId;
}
