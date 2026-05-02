package com.fixOnGo.backend.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fixOnGo.backend.entity.Worker;
import com.fixOnGo.backend.exception.ResourceNotFoundException;
import com.fixOnGo.backend.payload.WorkerSingUpStatus;
import com.fixOnGo.backend.repository.WorkerRepo;

@Service
public class AdminWorkerHandleService {

	@Autowired
	private WorkerRepo workerRepo;

	@Autowired
	private ModelMapper modelMapper;

	// Show All Workers To Admin
	public List<Worker> getWorkers(String city) {
		List<Worker> search = this.workerRepo.findByCity(city);
		return search;
	}

	// Update Worker SingUp Request APPROVED
	public WorkerSingUpStatus approveSignUpStatus(WorkerSingUpStatus dto, String email, String city) {

	    Worker worker = this.workerRepo.findWorkerByCityAndEmail(city, email);

	    if (worker == null) {
	        throw new ResourceNotFoundException("Worker", "Email", email);
	    }

	    worker.setWorkerRequestStatus(dto.getWorkerRequestStatus());

	    Worker updated = this.workerRepo.save(worker);

	    return this.modelMapper.map(updated, WorkerSingUpStatus.class);
	}

	// Update Worker SingUp Request REJECTED
	public WorkerSingUpStatus rejectSignUpStatus(WorkerSingUpStatus dto, String email, String city) {

	    Worker worker = this.workerRepo.findWorkerByCityAndEmail(city, email);

	    if (worker == null) {
	        throw new ResourceNotFoundException("Worker", "Email", email);
	    }

	    worker.setWorkerRequestStatus(dto.getWorkerRequestStatus());

	    Worker updated = this.workerRepo.save(worker);

	    return this.modelMapper.map(updated, WorkerSingUpStatus.class);
	}

}
