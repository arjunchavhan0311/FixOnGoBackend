package com.fixOnGo.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fixOnGo.backend.entity.ServicesHistory;

public interface ServiceHistoryRepo extends JpaRepository<ServicesHistory, Integer> {

	@Query("SELECT s FROM ServicesHistory s WHERE s.worker.worker_Id = :workerId")
	List<ServicesHistory> findServicesByWorkerId(@Param("workerId") Integer workerId);

	@Query("SELECT s FROM ServicesHistory s WHERE s.customer.customer_Id = :customerId")
	List<ServicesHistory> findServicesByCustomerId(@Param("customerId") Integer customerId);

}
