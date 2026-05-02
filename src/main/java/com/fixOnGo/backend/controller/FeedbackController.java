package com.fixOnGo.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fixOnGo.backend.entity.Feedback;
import com.fixOnGo.backend.payload.FeedbackDTO;
import com.fixOnGo.backend.service.FeedbackService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/feedback")
@CrossOrigin(origins = "*")
public class FeedbackController {


    @Autowired
    private FeedbackService feedbackService;

    // Customer adds feedback
    @PostMapping("/add")
    public ResponseEntity<Feedback> addFeedback(@Valid @RequestBody FeedbackDTO feedbackDTO) {

        Feedback feedback = feedbackService.addFeedback(feedbackDTO);

        return ResponseEntity.ok(feedback);
    }

    // Worker can see received feedback 
    @GetMapping("/worker/{workerId}")
    public ResponseEntity<List<Feedback>> getFeedbackByWorker(@PathVariable int workerId) {

        List<Feedback> feedbackList = feedbackService.getFeedbackByWorker(workerId);

        return ResponseEntity.ok(feedbackList);
    }

    // Customer can see feedback they gave
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Feedback>> getFeedbackByCustomer(@PathVariable int customerId) {

        List<Feedback> feedbackList = feedbackService.getFeedbackByCustomer(customerId);

        return ResponseEntity.ok(feedbackList);
    }

    // Admin can see all feedback
    @GetMapping("/all")
    public ResponseEntity<List<Feedback>> getAllFeedback() {

        List<Feedback> feedbackList = feedbackService.getAllFeedback();

        return ResponseEntity.ok(feedbackList);
    }
    
    @GetMapping("/service-rating/{serviceId}")
    public ResponseEntity<Double> getServiceRating(@PathVariable int serviceId) {

        Double rating = feedbackService.getServiceAverageRating(serviceId);

        return ResponseEntity.ok(rating);
    }
    
    @GetMapping("/worker-rating/{workerId}")
    public ResponseEntity<Double> getWorkerRating(@PathVariable int workerId) {

        Double rating = feedbackService.getWorkerAverageRating(workerId);

        return ResponseEntity.ok(rating);
    }
}
