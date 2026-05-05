package com.fixOnGo.backend.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fixOnGo.backend.entity.Customer;
import com.fixOnGo.backend.entity.Role;
import com.fixOnGo.backend.exception.ResourceAlreadyExistException;
import com.fixOnGo.backend.exception.ResourceNotFoundException;
import com.fixOnGo.backend.payload.CustomerProfile;
import com.fixOnGo.backend.payload.CustomerSingUp;
import com.fixOnGo.backend.payload.ForgotPasswordRequest;
import com.fixOnGo.backend.payload.LoginRequest;
import com.fixOnGo.backend.payload.LoginResponse;
import com.fixOnGo.backend.payload.ResetPasswordRequest;
import com.fixOnGo.backend.repository.CustomerRepo;
import com.fixOnGo.backend.security.JwtUtils;

import jakarta.mail.MessagingException;

@Service
public class CustomerService {

	@Autowired
	private EmailService emailService;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CustomerRepo customerRepo;

	// Customer Sign Up
	public CustomerSingUp signUp(CustomerSingUp customerSingUp) {

		if (customerRepo.findByEmail(customerSingUp.getEmail()) != null) {
			throw new ResourceAlreadyExistException("Customer", "Email", customerSingUp.getEmail());
		}

		Customer customer = this.modelMapper.map(customerSingUp, Customer.class);
		customer.setPassword(passwordEncoder.encode(customer.getPassword()));
		customer.setRole(Role.CUSTOMER);
		Customer signup = this.customerRepo.save(customer);

		return this.modelMapper.map(signup, CustomerSingUp.class);
	}

	// Customer Login
	public LoginResponse login(LoginRequest request) {

		Customer customer = customerRepo.findByEmail(request.getEmail());

		if (customer == null || !passwordEncoder.matches(request.getPassword(), customer.getPassword())) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
		}

		String token = jwtUtils.generateToken(customer.getEmail(), customer.getRole().name());
		return new LoginResponse(customer.getCustomer_Id(), token, customer.getEmail(), customer.getRole().name(),customer.getCity());
	}

	// Customer Show Profile
	public CustomerSingUp showProfile(String email) {

		Customer show = this.customerRepo.findByEmail(email);
		if (show == null) {
			throw new ResourceNotFoundException("Customer", "Email", email);
		}
		return this.modelMapper.map(show, CustomerSingUp.class);

	}

	// Customer Profile Update
	public CustomerProfile updateProfile(CustomerProfile customerProfile, String email) {

		Customer customer = this.customerRepo.findByEmail(email);
		if (customer == null) {
			throw new ResourceNotFoundException("Customer", "Email", email);
		}
		customer.setCustomer_name(customerProfile.getCustomer_name());
		customer.setCustomer_age(customerProfile.getCustomer_age());
		customer.setCustomer_gender(customerProfile.getCustomer_gender());
		customer.setStreet_address(customerProfile.getStreet_address());
		customer.setState(customerProfile.getState());
		customer.setDistrict(customerProfile.getDistrict());
		customer.setCity(customerProfile.getCity());
		customer.setPincode(customerProfile.getPincode());
		customer.setCustomer_office_address(customerProfile.getCustomer_office_address());
		customer.setCustomer_phoneno(customerProfile.getCustomer_phoneno());
		customer.setCustomer_profile_img(customerProfile.getCustomer_profile_img());

		Customer update = this.customerRepo.save(customer);

		return this.modelMapper.map(update, CustomerProfile.class);
	}

	// -------------------- Customer Forgot Password --------------------
	public ForgotPasswordRequest forgotPassword(ForgotPasswordRequest request) {

		Customer customer = customerRepo.findByEmail(request.getEmail());
		if (customer == null) {
			throw new ResourceNotFoundException("Customer", "Email", request.getEmail());
		}

		String otp = String.format("%06d", (int) (Math.random() * 900000 + 100000));

		customer.setResetToken(otp);
		customer.setResetTokenExpiry(java.time.LocalDateTime.now().plusMinutes(10));
		customerRepo.save(customer);

		try {
			emailService.sendOtpEmail(customer.getEmail(), otp);
		} catch (MessagingException e) {
			e.printStackTrace();
			throw new ResourceAlreadyExistException("OTP Send Fialed ", null, null);
		}

		return this.modelMapper.map(otp, ForgotPasswordRequest.class);
	}

	// -------------------- Reset Customer Password --------------------
	public ResetPasswordRequest resetPassword(ResetPasswordRequest request) {

		Customer customer = customerRepo.findByEmail(request.getEmail());
		if (customer == null)
			throw new ResourceNotFoundException("Customer", "Email", request.getEmail());

		if (customer.getResetToken() == null || !customer.getResetToken().equals(request.getResetToken())) {
			throw new ResourceAlreadyExistException("Invalid OTP", null, null);
		}

		if (customer.getResetTokenExpiry().isBefore(java.time.LocalDateTime.now())) {
			throw new ResourceAlreadyExistException("OTP Expired", null, null);
		}

		customer.setPassword(passwordEncoder.encode(request.getNewPassword()));
		customer.setResetToken(null);
		customer.setResetTokenExpiry(null);
		customerRepo.save(customer);

		return this.modelMapper.map(customer, ResetPasswordRequest.class);
	}
	
	
}
