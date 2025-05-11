package com.example.servicemarketplace.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Type;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serviceName;
    private String description;
    private Double price;
    private Integer durationInMinutes;

    // Each service belongs to one category
    @ManyToOne
    @JoinColumn(name = "category_id")
    private ServiceCategory category;

    // Each service belongs to one provider
    @ManyToOne
    @JoinColumn(name = "provider_id")
    private ServiceProvider provider;

    private boolean active = true;
}


