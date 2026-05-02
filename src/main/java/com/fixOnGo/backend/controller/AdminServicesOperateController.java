package com.fixOnGo.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fixOnGo.backend.entity.Services;
import com.fixOnGo.backend.entity.ServicesHistory;
import com.fixOnGo.backend.payload.ServicesDao;
import com.fixOnGo.backend.service.AdminServicesOperateService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin")
public class AdminServicesOperateController {

	@Autowired
	private AdminServicesOperateService adminServicesOperateService;

	// Create Service API
	@PostMapping(value = "/create/service", consumes = "multipart/form-data")
	public ResponseEntity<ServicesDao> createService(@Valid @RequestPart("service") ServicesDao servicesDao,
			@RequestPart("image") MultipartFile image) throws Exception {

		ServicesDao create = adminServicesOperateService.create(servicesDao, image);

		return new ResponseEntity<>(create, HttpStatus.CREATED);
	}

	// View All Services
	@GetMapping("/view/service")
	public ResponseEntity<List<Services>> viewAllService() {

		List<Services> viewAll = this.adminServicesOperateService.viewAllService();

		return new ResponseEntity<List<Services>>(viewAll, HttpStatus.OK);
	}

	// View All Service History
	@GetMapping("/view/service/history")
	public ResponseEntity<List<ServicesHistory>> showAll() {

		List<ServicesHistory> show = this.adminServicesOperateService.showAll();
		return new ResponseEntity<List<ServicesHistory>>(show, HttpStatus.OK);
	}
}
