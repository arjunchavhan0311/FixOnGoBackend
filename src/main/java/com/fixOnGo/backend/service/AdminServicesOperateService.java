package com.fixOnGo.backend.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fixOnGo.backend.entity.Services;
import com.fixOnGo.backend.entity.ServicesHistory;
import com.fixOnGo.backend.payload.ServicesDao;
import com.fixOnGo.backend.repository.ServiceHistoryRepo;
import com.fixOnGo.backend.repository.ServicesRepo;

@Service
public class AdminServicesOperateService {

	@Autowired
	private ServicesRepo servicesRepo;

	@Autowired
	private ServiceHistoryRepo historyRepo;

	@Autowired
	private ModelMapper modelMapper;

	private final String UPLOAD_DIR = "ProjectImages/ServiceDocuments";

	// Create Service
	public ServicesDao create(ServicesDao servicesDao, MultipartFile image) throws Exception {

		Services services = this.modelMapper.map(servicesDao, Services.class);
		File uploadDir = new File(UPLOAD_DIR);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}

		// Unique image name
		String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
		Path filePath = Paths.get(UPLOAD_DIR, fileName);
		Files.write(filePath, image.getBytes());

		// Save image path in DB

		services.setService_img("ServiceDocuments/" + fileName);

		Services create = this.servicesRepo.save(services);
		return this.modelMapper.map(create, ServicesDao.class);
	}

	// View All Service
	public List<Services> viewAllService() {

		List<Services> services = this.servicesRepo.findAll();
		return services;
	}

	// View All Service History
	public List<ServicesHistory> showAll() {

		List<ServicesHistory> viewAll = this.historyRepo.findAll();
		return viewAll;
	}

}
