package com.example.workplanner.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Temporal(TemporalType.DATE)
    private Date dueDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @ManyToOne
    @JoinColumn(name = "assigned_to_id")
    private Users assignedTo;

    @ManyToOne
    @JoinColumn(name = "suggested_by_id")
    private Users suggestedBy;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;




}

