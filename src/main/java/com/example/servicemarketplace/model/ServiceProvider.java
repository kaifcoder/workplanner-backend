package com.example.servicemarketplace.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceProvider implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String location;

  private String contact;

  @ManyToMany
  @JoinTable(
          name = "provider_category",
          joinColumns = @JoinColumn(name = "provider_id"),
          inverseJoinColumns = @JoinColumn(name = "category_id")
  )
  private List<ServiceCategory> serviceCategories;
}

