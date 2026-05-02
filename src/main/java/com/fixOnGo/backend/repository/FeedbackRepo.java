package com.fixOnGo.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fixOnGo.backend.entity.Feedback;

public interface FeedbackRepo extends JpaRepository<Feedback, Integer>{

	
	 // Get feedback for a particular worker
    @Query("SELECT f FROM Feedback f WHERE f.worker.worker_Id = :workerId")
    List<Feedback> findFeedbackByWorkerId(@Param("workerId") Integer workerId);

    // Get feedback given by a particular customer
    @Query("SELECT f FROM Feedback f WHERE f.customer.customer_Id = :customerId")
    List<Feedback> findFeedbackByCustomerId(@Param("customerId") Integer customerId);

    // Get feedback for a particular service
    @Query("SELECT f FROM Feedback f WHERE f.services.service_Id = :serviceId")
    List<Feedback> findFeedbackByServiceId(@Param("serviceId") Integer serviceId);
    
    
    // ⭐ average rating of service
    @Query("SELECT AVG(f.rating) FROM Feedback f WHERE f.services.service_Id = :serviceId")
    Double getAverageRatingByServiceId(@Param("serviceId") Integer serviceId);
    
    @Query("SELECT AVG(f.rating) FROM Feedback f WHERE f.worker.worker_Id = :workerId")
    Double getAverageRatingByWorkerId(@Param("workerId") Integer workerId);
}
