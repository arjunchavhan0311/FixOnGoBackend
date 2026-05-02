package com.fixOnGo.backend.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fixOnGo.backend.entity.ServiceStatus;
import com.fixOnGo.backend.entity.ServicesHistory;
import com.fixOnGo.backend.exception.ResourceNotFoundException;
import com.fixOnGo.backend.repository.ServiceHistoryRepo;

@Service
public class WorkerServicesResponce {

	@Autowired
	private ServiceHistoryRepo serviceHistoryRepo;

	// Show All Service Requests to Worker
	public List<ServicesHistory> showServices(int workerId) {
		return serviceHistoryRepo.findServicesByWorkerId(workerId);
	}

	// Update Customer Service Request
	public ServicesHistory updateService(ServiceStatus serviceStatus, int serviceid) {

		ServicesHistory existingService = serviceHistoryRepo.findById(serviceid)
				.orElseThrow(() -> new ResourceNotFoundException("Service", "Id", String.valueOf(serviceid)));

		existingService.setServiceStatus(serviceStatus);

		if (existingService.getServiceStatus().equals(ServiceStatus.COMPLETED)) {
			existingService.setCompletion_time(LocalTime.now());
			existingService.setCompletion_date(LocalDate.now());
		}

		return serviceHistoryRepo.save(existingService);
	}

}
