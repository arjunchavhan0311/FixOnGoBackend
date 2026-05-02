package com.fixOnGo.backend.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.fixOnGo.backend.entity.Role;
import com.fixOnGo.backend.entity.Worker;
import com.fixOnGo.backend.entity.WorkerRequestStatus;
import com.fixOnGo.backend.exception.ResourceAlreadyExistException;
import com.fixOnGo.backend.exception.ResourceNotFoundException;
import com.fixOnGo.backend.payload.ForgotPasswordRequest;
import com.fixOnGo.backend.payload.LoginRequest;
import com.fixOnGo.backend.payload.LoginResponse;
import com.fixOnGo.backend.payload.ResetPasswordRequest;
import com.fixOnGo.backend.payload.WorkerProfile;
import com.fixOnGo.backend.payload.WorkerSignUp;
import com.fixOnGo.backend.repository.WorkerRepo;
import com.fixOnGo.backend.security.JwtUtils;

import jakarta.mail.MessagingException;

@Service
public class WorkerService {

	@Autowired
	private EmailService emailService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private WorkerRepo workerRepo;

	private String saveFile(MultipartFile file, String folder) {

		try {
			String uploadDir = "ProjectImages/" + folder;
			Files.createDirectories(Paths.get(uploadDir));

			String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();

			Path path = Paths.get(uploadDir, filename);
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

			return "/uploads/" + folder + "/" + filename;

		} catch (Exception e) {
			throw new RuntimeException("Image upload failed");
		}
	}

	// Worker Sing Up
	public WorkerSignUp singUp(WorkerSignUp workerSignUp, MultipartFile aadharImg, MultipartFile panImg) {

		if (workerRepo.findByEmail(workerSignUp.getEmail()) != null) {
			throw new ResourceAlreadyExistException("Worker", "Email", workerSignUp.getEmail());
		}

		Worker worker = modelMapper.map(workerSignUp, Worker.class);
		worker.setPassword(passwordEncoder.encode(worker.getPassword()));
		worker.setRole(Role.WORKER);
		worker.setWorkerRequestStatus(WorkerRequestStatus.PENDING);

		// 📸 Save Images
		String aadharPath = saveFile(aadharImg, "WorkerDocuments/aadhar");
		String panPath = saveFile(panImg, "WorkerDocuments/pan");

		worker.setWorker_Aadhar_img(aadharPath);
		worker.setWorker_Pan_img(panPath);

		Worker saved = workerRepo.save(worker);
		return modelMapper.map(saved, WorkerSignUp.class);
	}

	// Worker Login
	public LoginResponse login(LoginRequest request) {

		Worker worker = workerRepo.findByEmail(request.getEmail());

		if (worker == null || !passwordEncoder.matches(request.getPassword(), worker.getPassword())) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
		} else if (worker.getWorkerRequestStatus() == WorkerRequestStatus.PENDING
				|| worker.getWorkerRequestStatus() == WorkerRequestStatus.REJECTED) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Admin Not Approve Your Request Wait or Retry");
		}

		String token = jwtUtils.generateToken(worker.getEmail(), worker.getRole().name());
		return new LoginResponse(worker.getWorker_Id(), token, worker.getEmail(), worker.getRole().name(), worker.getCity());
	}

	// Worker Show Profile
	public WorkerSignUp showProfile(String email) {

		Worker worker = this.workerRepo.findByEmail(email);
		if (worker == null) {
			throw new ResourceNotFoundException("Worker", "Email", email);
		}

		return this.modelMapper.map(worker, WorkerSignUp.class);
	}

	// Worker Profile Update
	public WorkerProfile updateProfile(WorkerProfile workerProfile, String email) {

		Worker worker = this.workerRepo.findByEmail(email);
		if (worker == null) {
			throw new ResourceNotFoundException("Worker", "Email", email);
		}
		worker.setWorker_name(workerProfile.getWorker_name());
		worker.setWorker_age(workerProfile.getWorker_age());
		worker.setWorker_gender(workerProfile.getWorker_gender());
		worker.setStreet_address(workerProfile.getStreet_address());
		worker.setState(workerProfile.getState());
		worker.setDistrict(workerProfile.getDistrict());
		worker.setCity(workerProfile.getCity());
		worker.setPincode(workerProfile.getPincode());
		worker.setWorker_phoneno(workerProfile.getWorker_phoneno());
		worker.setWorker_bio(workerProfile.getWorker_bio());
		worker.setServiceCategory(workerProfile.getServiceCategory());
		worker.setWorker_certificates(workerProfile.getWorker_certificates());
		worker.setWorker_education(workerProfile.getWorker_education());
		worker.setWorker_experience(workerProfile.getWorker_experience());
		worker.setWorker_languages(workerProfile.getWorker_languages());
		worker.setWorker_status(workerProfile.getWorker_status());

		Worker update = this.workerRepo.save(worker);

		return this.modelMapper.map(update, WorkerProfile.class);
	}

	// -------------------- Worker Forgot Password --------------------
	public ForgotPasswordRequest forgotPassword(ForgotPasswordRequest request) {

		Worker worker = workerRepo.findByEmail(request.getEmail());
		if (worker == null) {
			throw new ResourceNotFoundException("Admin", "Email", request.getEmail());
		}

		String otp = String.format("%06d", (int) (Math.random() * 900000 + 100000));

		worker.setResetToken(otp);
		worker.setResetTokenExpiry(java.time.LocalDateTime.now().plusMinutes(10));
		workerRepo.save(worker);

		try {
			emailService.sendOtpEmail(worker.getEmail(), otp);
		} catch (MessagingException e) {
			e.printStackTrace();
			throw new ResourceAlreadyExistException("OTP Send Fialed ", null, null);
		}

		return this.modelMapper.map(otp, ForgotPasswordRequest.class);
	}

	// -------------------- Reset Worker Password --------------------
	public ResetPasswordRequest resetPassword(ResetPasswordRequest request) {

		Worker worker = workerRepo.findByEmail(request.getEmail());
		if (worker == null)
			throw new ResourceNotFoundException("Admin", "Email", request.getEmail());

		if (worker.getResetToken() == null || !worker.getResetToken().equals(request.getResetToken())) {
			throw new ResourceAlreadyExistException("Invalid OTP ", null, null);
		}

		if (worker.getResetTokenExpiry().isBefore(java.time.LocalDateTime.now())) {
			throw new ResourceAlreadyExistException("OTP Expired", null, null);
		}

		worker.setPassword(passwordEncoder.encode(request.getNewPassword()));
		worker.setResetToken(null);
		worker.setResetTokenExpiry(null);
		workerRepo.save(worker);

		return this.modelMapper.map(worker, ResetPasswordRequest.class);
	}

}
