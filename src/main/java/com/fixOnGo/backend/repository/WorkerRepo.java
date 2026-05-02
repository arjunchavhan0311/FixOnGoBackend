package com.fixOnGo.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fixOnGo.backend.entity.ServiceCategory;
import com.fixOnGo.backend.entity.Worker;
import com.fixOnGo.backend.entity.WorkerRequestStatus;

import java.util.List;

public interface WorkerRepo extends JpaRepository<Worker, Integer> {

	Worker findByEmail(String email);

	List<Worker> findByWorkerRequestStatus(WorkerRequestStatus workerRequestStatus);

	List<Worker> findByServiceCategory(ServiceCategory serviceCategory);
	

	@Query("""
		    SELECT w FROM Worker w
		    WHERE w.city = :city
		    AND w.serviceCategory = :serviceCategory
		    AND w.worker_status = 'ACTIVE'
		    AND w.workerRequestStatus = 'APPROVED'
		""")
		List<Worker> findWorkersByCityAndService(
		        @Param("city") String city,
		        @Param("serviceCategory") ServiceCategory serviceCategory
		);
	
	List<Worker> findByCity(String city);
	
	@Query("""
			SELECT w FROM Worker w
			WHERE w.city = :city
			AND w.email = :email
			AND w.workerRequestStatus = 'PENDING'
			""")
			Worker findWorkerByCityAndEmail(
			        @Param("city") String city,
			        @Param("email") String email
			);
}
