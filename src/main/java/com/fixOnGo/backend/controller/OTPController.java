package com.fixOnGo.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fixOnGo.backend.entity.ServiceStatus;
import com.fixOnGo.backend.entity.ServicesHistory;
import com.fixOnGo.backend.payload.OTPRequest;
import com.fixOnGo.backend.repository.ServiceHistoryRepo;
import com.fixOnGo.backend.service.OTPService;

@RestController
@RequestMapping("/api/service")
public class OTPController {

    @Autowired
    private OTPService otpService;

    @Autowired
    private ServiceHistoryRepo serviceRepo;

    // 1. Send OTP
    @PostMapping("/send-otp/{serviceId}")
    public ResponseEntity<?> sendOtp(@PathVariable int serviceId) {

        ServicesHistory service = serviceRepo.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        String email = service.getCustomer().getEmail();
        String phone = service.getCustomer().getCustomer_phoneno();

        otpService.sendOtp(serviceId, email, phone);

        return ResponseEntity.ok("OTP sent successfully");
    }

    // 2. Verify OTP
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody OTPRequest request) {

        boolean isValid = otpService.verifyOtp(
                request.getServiceId(),
                request.getOtp()
        );

        if (isValid) {
            // Update service status
            ServicesHistory service = serviceRepo.findById(request.getServiceId()).get();
            service.setServiceStatus(ServiceStatus.IN_PROGRESS);
            serviceRepo.save(service);

            return ResponseEntity.ok("OTP verified & job started");
        }

        return ResponseEntity.badRequest().body("Invalid OTP");
    }
}