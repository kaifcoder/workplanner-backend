package com.example.workplanner.service.impl;

import com.example.workplanner.model.Feedback;
import com.example.workplanner.repository.FeedbackRepository;
import com.example.workplanner.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FeedbackServiceImpl implements FeedbackService {

  @Autowired
  private FeedbackRepository FeedbackRepository;

  @Override
  public List<Feedback> findAll() {
    return FeedbackRepository.findAll();
  }

  @Override
  public Feedback findById(Long id) {
    Optional<Feedback> entity = FeedbackRepository.findById(id);
    return entity.orElse(null);
  }

  @Override
  public Feedback save(Feedback entity) {
    return FeedbackRepository.save(entity);
  }

  @Override
  public void delete(Long id) {
    FeedbackRepository.deleteById(id);
  }
}
