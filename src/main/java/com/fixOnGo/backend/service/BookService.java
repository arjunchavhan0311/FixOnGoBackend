package com.fixOnGo.backend.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fixOnGo.backend.entity.Customer;
import com.fixOnGo.backend.entity.ServiceCategory;
import com.fixOnGo.backend.entity.Services;
import com.fixOnGo.backend.entity.ServicesHistory;
import com.fixOnGo.backend.entity.Worker;
import com.fixOnGo.backend.payload.ServiceHistoryDao;
import com.fixOnGo.backend.repository.CustomerRepo;
import com.fixOnGo.backend.repository.ServiceHistoryRepo;
import com.fixOnGo.backend.repository.ServicesRepo;
import com.fixOnGo.backend.repository.WorkerRepo;

@Service
public class BookService {

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private ServicesRepo servicesRepo;

	@Autowired
	private ServiceHistoryRepo serviceHistoryRepo;

	@Autowired
	private WorkerRepo workerRepo;

	@Autowired
	private ModelMapper modelMapper;

	private final String UPLOAD_DIR = "ProjectImages/CustomerDocuments";

	// Show All Services To Customer
	public List<Services> showServices() {

		List<Services> allServices = this.servicesRepo.findAll();

		return allServices;
	}

	// Show All Worker According to Service Category
	public List<Worker> showWorker(String city, ServiceCategory serviceCategory) {
		
		List<Worker> workerlist=this.workerRepo.findWorkersByCityAndService(city, serviceCategory);

		return workerlist;
	}

	// Book Service
	public ServiceHistoryDao confirmService(ServiceHistoryDao dto, MultipartFile image) throws Exception {

		ServicesHistory history = new ServicesHistory();

		history.setCustomer_available_phoneno(dto.getCustomer_available_phoneno());
		history.setCustomer_Issues(dto.getCustomer_Issues());
		history.setService_location(dto.getService_location());
		history.setBooking_date(dto.getBooking_date());
		history.setBooking_time(dto.getBooking_time());
		history.setAvailable_date(dto.getAvailable_date());
		history.setAvailable_time(dto.getAvailable_time());
		history.setPaymentStatus(dto.getPaymentStatus());
		history.setServiceStatus(dto.getServiceStatus());
		history.setPaymentType(dto.getPaymentType());

		// 🔥 FETCH ACTUAL ENTITIES
		Customer customer = customerRepo.findById(dto.getCustomerId())
				.orElseThrow(() -> new RuntimeException("Customer not found"));

		Worker worker = workerRepo.findById(dto.getWorkerId())
				.orElseThrow(() -> new RuntimeException("Worker not found"));

		Services service = servicesRepo.findById(dto.getServiceId())
				.orElseThrow(() -> new RuntimeException("Service not found"));

		history.setCustomer(customer);
		history.setWorker(worker);
		history.setServices(service);

		// HANDLE IMAGE
		if (image != null && !image.isEmpty()) {
			String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
			Path filePath = Paths.get(UPLOAD_DIR, fileName);
			Files.write(filePath, image.getBytes());
			history.setIssue_evidence_img("CustomerDocuments/" + fileName);
		}

		ServicesHistory saved = serviceHistoryRepo.save(history);

		return modelMapper.map(saved, ServiceHistoryDao.class);
	}

	// View Service History

	public List<ServicesHistory> showHistory(Integer customer_id) {

		List<ServicesHistory> viewAll = this.serviceHistoryRepo.findServicesByCustomerId(customer_id);

		return viewAll;
	}
	
	// Track Service Details
	
	public ServicesHistory trackService(Integer historyId) {

	    ServicesHistory history = serviceHistoryRepo.findById(historyId)
	            .orElseThrow(() -> new RuntimeException("Service history not found"));

	    return history;
	}

}
