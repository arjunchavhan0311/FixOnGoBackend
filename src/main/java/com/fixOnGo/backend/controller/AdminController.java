package com.fixOnGo.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fixOnGo.backend.payload.AdminProfile;
import com.fixOnGo.backend.payload.AdminSignUp;
import com.fixOnGo.backend.payload.ForgotPasswordRequest;
import com.fixOnGo.backend.payload.LoginRequest;
import com.fixOnGo.backend.payload.LoginResponse;
import com.fixOnGo.backend.payload.ResetPasswordRequest;
import com.fixOnGo.backend.service.AdminService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AdminController {

	@Autowired
	private AdminService adminService;

	// Admin Sing Up API
	@PostMapping("/signup")
	public ResponseEntity<AdminSignUp> singUp(@Valid @RequestBody AdminSignUp adminSignUp) {

		AdminSignUp create = this.adminService.signUp(adminSignUp);

		return new ResponseEntity<AdminSignUp>(create, HttpStatus.CREATED);
	}

	// Admin Login API
	@PostMapping("/login")
	public LoginResponse login(@RequestBody LoginRequest request) {
		return adminService.login(request);
	}

	// Admin Show Profile API
	@GetMapping("/profile/{email}")
	public ResponseEntity<AdminProfile> showProfile(@PathVariable String email) {
		AdminProfile getprofile = this.adminService.showProfile(email);
		return ResponseEntity.ok(getprofile);
	}

	// Admin Update Profile API
	@PutMapping("/update/{email}")
	public ResponseEntity<AdminProfile> updateProfile(@PathVariable String email,
			@Valid @RequestBody AdminProfile adminProfile) {

		AdminProfile update = this.adminService.updateProfile(adminProfile, email);
		return new ResponseEntity<AdminProfile>(update, HttpStatus.OK);
	}

	// Admin Forgot Password API
	@PostMapping("/forgot-password")
	public ResponseEntity<ForgotPasswordRequest> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {

		ForgotPasswordRequest forgotpass = this.adminService.forgotPassword(request);

		return new ResponseEntity<ForgotPasswordRequest>(forgotpass, HttpStatus.OK);
	}

	// Reset Admin Password
	@PostMapping("/reset-password")
	public ResponseEntity<ResetPasswordRequest> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {

		ResetPasswordRequest resetpass = this.adminService.resetPassword(request);

		return new ResponseEntity<ResetPasswordRequest>(resetpass, HttpStatus.OK);
	}

}
