package com.fixOnGo.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fixOnGo.backend.payload.ForgotPasswordRequest;
import com.fixOnGo.backend.payload.LoginRequest;
import com.fixOnGo.backend.payload.LoginResponse;
import com.fixOnGo.backend.payload.ResetPasswordRequest;
import com.fixOnGo.backend.payload.WorkerProfile;
import com.fixOnGo.backend.payload.WorkerSignUp;
import com.fixOnGo.backend.service.WorkerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/worker")
public class WorkerController {

	@Autowired
	private WorkerService workerService;

	// Worker Sing Up API
	@PostMapping(value = "/signup", consumes = "multipart/form-data")
	public ResponseEntity<WorkerSignUp> singUp(@RequestPart("data") @Valid WorkerSignUp workerSignUp,
			@RequestPart("aadharImg") MultipartFile aadharImg, @RequestPart("panImg") MultipartFile panImg) {

		WorkerSignUp create = workerService.singUp(workerSignUp, aadharImg, panImg);

		return new ResponseEntity<>(create, HttpStatus.CREATED);
	}

	// Worker Login API
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

		LoginResponse logIn = this.workerService.login(request);

		return new ResponseEntity<LoginResponse>(logIn, HttpStatus.OK);
	}

	// Worker Show Profile
	@GetMapping("/profile/{email}")
	public ResponseEntity<WorkerSignUp> showProfile(@PathVariable String email) {

		WorkerSignUp workerSignUp = this.workerService.showProfile(email);

		return new ResponseEntity<WorkerSignUp>(workerSignUp, HttpStatus.FOUND);
	}

	// Worker Update Profile
	@PutMapping("/update/{email}")
	public ResponseEntity<WorkerProfile> updateProfile(@PathVariable String email,
			@Valid @RequestBody WorkerProfile workerProfile) {

		WorkerProfile worker = this.workerService.updateProfile(workerProfile, email);

		return new ResponseEntity<WorkerProfile>(worker, HttpStatus.OK);
	}

	// Admin Forgot Password API
	@PostMapping("/forgot-password")
	public ResponseEntity<ForgotPasswordRequest> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {

		ForgotPasswordRequest forgotpass = this.workerService.forgotPassword(request);

		return new ResponseEntity<ForgotPasswordRequest>(forgotpass, HttpStatus.OK);
	}

	// Reset Admin Password
	@PostMapping("/reset-password")
	public ResponseEntity<ResetPasswordRequest> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {

		ResetPasswordRequest resetpass = this.workerService.resetPassword(request);

		return new ResponseEntity<ResetPasswordRequest>(resetpass, HttpStatus.OK);
	}

}
