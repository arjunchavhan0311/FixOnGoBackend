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
import org.springframework.web.bind.annotation.RestController;

import com.fixOnGo.backend.payload.CustomerProfile;
import com.fixOnGo.backend.payload.CustomerSingUp;
import com.fixOnGo.backend.payload.ForgotPasswordRequest;
import com.fixOnGo.backend.payload.LoginRequest;
import com.fixOnGo.backend.payload.LoginResponse;
import com.fixOnGo.backend.payload.ResetPasswordRequest;
import com.fixOnGo.backend.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	// Customer Sing Up API
	@PostMapping("/signup")
	public ResponseEntity<CustomerSingUp> singUp(@Valid @RequestBody CustomerSingUp customerSingUp) {

		CustomerSingUp create = this.customerService.signUp(customerSingUp);

		return new ResponseEntity<CustomerSingUp>(create, HttpStatus.CREATED);
	}

	// Customer Login API
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

		LoginResponse logIn = this.customerService.login(request);

		return new ResponseEntity<LoginResponse>(logIn, HttpStatus.OK);
	}

	// Customer Show Profile API
	@GetMapping("/profile/{email}")
	public ResponseEntity<CustomerSingUp> showProfile(@PathVariable String email) {

		CustomerSingUp show = this.customerService.showProfile(email);
		return new ResponseEntity<CustomerSingUp>(show, HttpStatus.OK);
	}

	// Customer Update Profile API
	@PutMapping("/update/{email}")
	public ResponseEntity<CustomerProfile> updateProfile(@PathVariable String email,
			@Valid @RequestBody CustomerProfile customerProfile) {

		CustomerProfile update = this.customerService.updateProfile(customerProfile, email);

		return new ResponseEntity<CustomerProfile>(update, HttpStatus.OK);
	}

	// Admin Forgot Password API
	@PostMapping("/forgot-password")
	public ResponseEntity<ForgotPasswordRequest> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {

		ForgotPasswordRequest forgotpass = this.customerService.forgotPassword(request);

		return new ResponseEntity<ForgotPasswordRequest>(forgotpass, HttpStatus.OK);
	}

	// Reset Admin Password
	@PostMapping("/reset-password")
	public ResponseEntity<ResetPasswordRequest> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {

		ResetPasswordRequest resetpass = this.customerService.resetPassword(request);

		return new ResponseEntity<ResetPasswordRequest>(resetpass, HttpStatus.OK);
	}

}
