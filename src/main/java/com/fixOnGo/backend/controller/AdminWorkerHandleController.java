package com.fixOnGo.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fixOnGo.backend.entity.Worker;
import com.fixOnGo.backend.payload.WorkerSingUpStatus;
import com.fixOnGo.backend.service.AdminWorkerHandleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin")
public class AdminWorkerHandleController {

	@Autowired
	private AdminWorkerHandleService adminWorkerHandleService;

	// Get All Workers
	@GetMapping("/workers/{city}")
	public ResponseEntity<List<Worker>> getWorkers(@PathVariable String city) {
		// List<Worker> list=this.adminWorkerHandleService.getWorkers();
		// return new ResponseEntity<>(list, HttpStatus.OK);
		return ResponseEntity.ok(adminWorkerHandleService.getWorkers(city));
	}

	// Update Worker SingUp Request Status Approve
	@PutMapping("/workers/{email}/{city}/approved")
	public ResponseEntity<WorkerSingUpStatus> approveSignUpStatus(@PathVariable String email, @PathVariable String city,
			@Valid @RequestBody WorkerSingUpStatus worker) {
		WorkerSingUpStatus updateStatus = this.adminWorkerHandleService.approveSignUpStatus(worker, email, city);

		return new ResponseEntity<WorkerSingUpStatus>(updateStatus, HttpStatus.OK);
	}

	// Update Worker SingUp Request Status Reject
	@PutMapping("/workers/{email}/{city}/rejected")
	public ResponseEntity<WorkerSingUpStatus> rejectSignUpStatus(@PathVariable String email,@PathVariable String city,
			@Valid @RequestBody WorkerSingUpStatus worker) {
		WorkerSingUpStatus updateStatus = this.adminWorkerHandleService.rejectSignUpStatus(worker, email,city);

		return new ResponseEntity<WorkerSingUpStatus>(updateStatus, HttpStatus.OK);
	}
}
