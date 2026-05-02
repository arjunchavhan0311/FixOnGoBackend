package com.fixOnGo.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fixOnGo.backend.entity.ServiceCategory;
import com.fixOnGo.backend.entity.Services;
import com.fixOnGo.backend.entity.ServicesHistory;
import com.fixOnGo.backend.entity.Worker;
import com.fixOnGo.backend.payload.ServiceHistoryDao;
import com.fixOnGo.backend.service.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customer")
public class BookServiceController {

	@Autowired
	private BookService bookService;

	// Show All Services To Customer
	@GetMapping("/show/service")
	public ResponseEntity<List<Services>> showServices() {

		List<Services> showAll = this.bookService.showServices();
		return new ResponseEntity<List<Services>>(showAll, HttpStatus.OK);
	}

	// Show All Worker According to Service Category
	@GetMapping("/show/worker/{city}/{serviceCategory}")
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<List<Worker>> showWorker(@PathVariable String city,@PathVariable ServiceCategory serviceCategory) {

		return ResponseEntity.ok(this.bookService.showWorker(city,serviceCategory));
	}

	// Book Service
	@PostMapping(value = "/service/book", consumes = "multipart/form-data")
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<ServiceHistoryDao> confirmService(
			@Valid @RequestPart("serviceHistoryDao") ServiceHistoryDao serviceHistoryDao,
			@RequestPart(value = "image", required = false) MultipartFile image) throws Exception {

		return ResponseEntity.ok(this.bookService.confirmService(serviceHistoryDao, image));
	}

	@GetMapping("show/service/history/{id}")
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<List<ServicesHistory>> showHistory(@PathVariable("id") Integer customer_Id) {

		List<ServicesHistory> viewAll = this.bookService.showHistory(customer_Id);

		return new ResponseEntity<List<ServicesHistory>>(viewAll, HttpStatus.OK);
	}
	
	// Track Service Details
	@GetMapping("/track/service/{historyId}")
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<ServicesHistory> trackService(@PathVariable Integer historyId) {

	    ServicesHistory service = this.bookService.trackService(historyId);

	    return new ResponseEntity<>(service, HttpStatus.OK);
	}

}
