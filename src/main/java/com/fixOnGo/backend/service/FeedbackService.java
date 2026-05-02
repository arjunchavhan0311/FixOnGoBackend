package com.fixOnGo.backend.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fixOnGo.backend.entity.Customer;
import com.fixOnGo.backend.entity.Feedback;
import com.fixOnGo.backend.entity.Services;
import com.fixOnGo.backend.entity.ServicesHistory;
import com.fixOnGo.backend.entity.Worker;
import com.fixOnGo.backend.payload.FeedbackDTO;
import com.fixOnGo.backend.repository.CustomerRepo;
import com.fixOnGo.backend.repository.FeedbackRepo;
import com.fixOnGo.backend.repository.ServiceHistoryRepo;
import com.fixOnGo.backend.repository.ServicesRepo;
import com.fixOnGo.backend.repository.WorkerRepo;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepo feedbackRepo;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private WorkerRepo workerRepo;

    @Autowired
    private ServicesRepo servicesRepo;
    
    @Autowired
    private ServiceHistoryRepo historyRepo;


    // Add Feedback
    public Feedback addFeedback(FeedbackDTO feedbackDTO) {

        Customer customer = customerRepo.findById(feedbackDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Worker worker = workerRepo.findById(feedbackDTO.getWorkerId())
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        Services service = servicesRepo.findById(feedbackDTO.getServiceId())
                .orElseThrow(() -> new RuntimeException("Service not found"));

        ServicesHistory history = historyRepo.findById(feedbackDTO.getHistoryId())
                .orElseThrow(() -> new RuntimeException("Service history not found"));

        Feedback feedback = new Feedback();

        feedback.setDescription(feedbackDTO.getDescription());
        feedback.setRating(feedbackDTO.getRating());

        feedback.setCustomer(customer);
        feedback.setWorker(worker);
        feedback.setServices(service);

        feedback.setServicesHistory(history);

        feedback.setFeedback_date(LocalDate.now());
        feedback.setFeedback_time(LocalTime.now());
        
        return feedbackRepo.save(feedback);
        
    }

    // Worker can see feedback
    public List<Feedback> getFeedbackByWorker(int workerId) {
        return feedbackRepo.findFeedbackByWorkerId(workerId);
    }

    // Customer can see feedback
    public List<Feedback> getFeedbackByCustomer(int customerId) {
        return feedbackRepo.findFeedbackByCustomerId(customerId);
    }

    // Admin can see all feedback
    public List<Feedback> getAllFeedback() {
        return feedbackRepo.findAll();
    }
    
    public Double getServiceAverageRating(int serviceId) {
        Double rating = feedbackRepo.getAverageRatingByServiceId(serviceId);

        if (rating == null) {
            return 0.0;
        }

        return Math.round(rating * 10.0) / 10.0; // round to 1 decimal
    }
    
    public Double getWorkerAverageRating(int workerId) {

        Double rating = feedbackRepo.getAverageRatingByWorkerId(workerId);

        if (rating == null) {
            return 0.0;
        }

        return Math.round(rating * 10.0) / 10.0;
    }
}