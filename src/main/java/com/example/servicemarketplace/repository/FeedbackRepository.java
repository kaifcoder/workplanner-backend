package com.example.servicemarketplace.repository;

import com.example.servicemarketplace.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {}
