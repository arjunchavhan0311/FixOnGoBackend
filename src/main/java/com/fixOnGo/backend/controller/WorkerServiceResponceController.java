package com.fixOnGo.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fixOnGo.backend.entity.ServiceStatus;
import com.fixOnGo.backend.entity.ServicesHistory;
import com.fixOnGo.backend.service.WorkerServicesResponce;

@RestController
@RequestMapping("/api/worker")
@CrossOrigin(origins = "http://localhost:3000")
public class WorkerServiceResponceController {

	@Autowired
	private WorkerServicesResponce workerServicesResponce;

	// Show All Service Requests to Worker
	@GetMapping("/show/services/{id}")
	public ResponseEntity<List<ServicesHistory>> showServices(@PathVariable("id") int worker_Id) {

		List<ServicesHistory> showAll = this.workerServicesResponce.showServices(worker_Id);

		return new ResponseEntity<List<ServicesHistory>>(showAll, HttpStatus.OK);
	}

	// Update Customer Service Request
	@PutMapping("/update/service/status/{serviceStatus}/{id}")
	public ResponseEntity<ServicesHistory> updateService(@PathVariable ServiceStatus serviceStatus,
			@PathVariable("id") int serviceId) {

		ServicesHistory acceptService = this.workerServicesResponce.updateService(serviceStatus, serviceId);

		return new ResponseEntity<ServicesHistory>(acceptService, HttpStatus.OK);
	}

}
