package com.fixOnGo.backend.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fixOnGo.backend.entity.Admin;
import com.fixOnGo.backend.entity.Role;
import com.fixOnGo.backend.exception.ResourceAlreadyExistException;
import com.fixOnGo.backend.exception.ResourceNotFoundException;
import com.fixOnGo.backend.payload.AdminProfile;
import com.fixOnGo.backend.payload.AdminSignUp;
import com.fixOnGo.backend.payload.ForgotPasswordRequest;
import com.fixOnGo.backend.payload.LoginRequest;
import com.fixOnGo.backend.payload.LoginResponse;
import com.fixOnGo.backend.payload.ResetPasswordRequest;
import com.fixOnGo.backend.repository.AdminRepo;
import com.fixOnGo.backend.security.JwtUtils;

import jakarta.mail.MessagingException;

@Service
public class AdminService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private EmailService emailService;

	@Autowired
	private AdminRepo adminRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtils jwtUtils;

	// -------------------- Admin Sign Up --------------------
	public AdminSignUp signUp(AdminSignUp adminSignUp) {

		Admin admin = this.modelMapper.map(adminSignUp, Admin.class);
		if (this.adminRepo.findByEmail(admin.getEmail()) != null) {
			throw new ResourceAlreadyExistException("Admin", "Email", admin.getEmail());
		}
		admin.setPassword(passwordEncoder.encode(admin.getPassword()));
		admin.setRole(Role.ADMIN);
		Admin signup = this.adminRepo.save(admin);

		return this.modelMapper.map(signup, AdminSignUp.class);
	}

	// -------------------- Admin Login --------------------
	public LoginResponse login(LoginRequest request) {

		Admin admin = adminRepo.findByEmail(request.getEmail());

		if (admin == null || !passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
		}

		String token = jwtUtils.generateToken(admin.getEmail(), admin.getRole().name());

		return new LoginResponse(admin.getAdmin_Id(), token, admin.getEmail(), admin.getRole().name(),admin.getCity());
	}

	// -------------------- Admin Profile Page --------------------
	public AdminProfile showProfile(String email) {

		Admin admin = this.adminRepo.findByEmail(email);
		if (admin == null) {
			throw new ResourceNotFoundException("Admin", "Email", email);
		}
		return this.modelMapper.map(admin, AdminProfile.class);
	}

	// -------------------- Admin Profile Update --------------------
	public AdminProfile updateProfile(AdminProfile adminProfile, String email) {
		Admin admin = this.adminRepo.findByEmail(email);
		if (admin == null) {
			throw new ResourceNotFoundException("Admin", "Email", email);
		}
		admin.setAdmin_name(adminProfile.getAdmin_name());
		admin.setAdmin_age(adminProfile.getAdmin_age());
		admin.setAdmin_gender(adminProfile.getAdmin_gender());
		admin.setStreet_address(adminProfile.getStreet_address());
		admin.setState(adminProfile.getState());
		admin.setDistrict(adminProfile.getDistrict());
		admin.setCity(adminProfile.getCity());
		admin.setPincode(adminProfile.getPincode());
		admin.setAdmin_phoneno(adminProfile.getAdmin_phoneno());
		admin.setAdmin_profile_img(adminProfile.getAdmin_profile_img());
		admin.setRole(Role.ADMIN);

		Admin update = this.adminRepo.save(admin);

		return this.modelMapper.map(update, AdminProfile.class);

	}

	// -------------------- Admin Forgot Password --------------------
	public ForgotPasswordRequest forgotPassword(ForgotPasswordRequest request) {

		Admin admin = adminRepo.findByEmail(request.getEmail());
		if (admin == null) {
			throw new ResourceNotFoundException("Admin", "Email", request.getEmail());
		}

		String otp = String.format("%06d", (int) (Math.random() * 900000 + 100000));

		admin.setResetToken(otp);
		admin.setResetTokenExpiry(java.time.LocalDateTime.now().plusMinutes(10));
		adminRepo.save(admin);

		try {
			emailService.sendOtpEmail(admin.getEmail(), otp);
		} catch (MessagingException e) {
			e.printStackTrace();
			throw new ResourceAlreadyExistException("OTP Send Fialed", null, null);
		}

		return this.modelMapper.map(otp, ForgotPasswordRequest.class);
	}

	// -------------------- Reset Admin Password --------------------
	public ResetPasswordRequest resetPassword(ResetPasswordRequest request) {

		Admin admin = adminRepo.findByEmail(request.getEmail());
		if (admin == null)
			throw new ResourceNotFoundException("Admin", "Email", request.getEmail());

		if (admin.getResetToken() == null || !admin.getResetToken().equals(request.getResetToken())) {
			throw new ResourceAlreadyExistException("Invalid OTP", null, null);
		}

		if (admin.getResetTokenExpiry().isBefore(java.time.LocalDateTime.now())) {
			throw new ResourceAlreadyExistException("OTP Expired", null, null);
		}

		admin.setPassword(passwordEncoder.encode(request.getNewPassword()));
		admin.setResetToken(null);
		admin.setResetTokenExpiry(null);
		adminRepo.save(admin);

		return this.modelMapper.map(admin, ResetPasswordRequest.class);
	}
}
