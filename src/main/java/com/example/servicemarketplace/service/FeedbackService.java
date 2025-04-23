package com.example.servicemarketplace.service;

import com.example.servicemarketplace.model.Feedback;
import java.util.List;

public interface FeedbackService {
  List<Feedback> findAll();
  Feedback findById(Long id);
  Feedback save(Feedback entity);
  void delete(Long id);
}
