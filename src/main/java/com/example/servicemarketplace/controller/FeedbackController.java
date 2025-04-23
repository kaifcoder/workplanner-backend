package com.example.servicemarketplace.controller;

import com.example.servicemarketplace.model.Feedback;
import com.example.servicemarketplace.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

  @Autowired
  private FeedbackService FeedbackService;

  @GetMapping
  public List<Feedback> getAll() {
    return FeedbackService.findAll();
  }

  @GetMapping("/{id}")
  public Feedback getById(@PathVariable Long id) {
    return FeedbackService.findById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Feedback create(@RequestBody Feedback entity) {
    return FeedbackService.save(entity);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    FeedbackService.delete(id);
  }
}
