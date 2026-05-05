package com.fixOnGo.backend.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.fixOnGo.backend.entity.ServiceOTP;
import com.fixOnGo.backend.repository.ServiceOtpRepository;

@Service
public class OTPService {

    @Autowired
    private ServiceOtpRepository otpRepo;

    @Autowired
    private JavaMailSender mailSender;

    public void sendOtp(int serviceId, String email, String phone) {

        String otp = String.valueOf((int)(Math.random() * 9000) + 1000);

        ServiceOTP serviceOtp = otpRepo.findByServiceId(serviceId)
                .orElse(new ServiceOTP());

        serviceOtp.setServiceId(serviceId);
        serviceOtp.setOtp(otp);
        serviceOtp.setVerified(false);
        serviceOtp.setExpiryTime(LocalDateTime.now().plusMinutes(5));

        otpRepo.save(serviceOtp);

        // Send Email
        sendEmail(email, otp);

        // Send SMS (dummy for now)
        sendSms(phone, otp);
    }

    private void sendEmail(String to, String otp) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setSubject("Service OTP Verification");
        msg.setText("Your OTP is: " + otp);

        mailSender.send(msg);
    }

    private void sendSms(String phone, String otp) {
        System.out.println("SMS to " + phone + " OTP: " + otp);
        // Integrate Twilio / Fast2SMS here
    }
    
    public boolean verifyOtp(int serviceId, String enteredOtp) {

    	ServiceOTP serviceOtp = otpRepo.findByServiceId(serviceId)
                .orElseThrow(() -> new RuntimeException("OTP not found"));

        if (serviceOtp.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired");
        }

        if (!serviceOtp.getOtp().equals(enteredOtp)) {
            throw new RuntimeException("Invalid OTP");
        }

        serviceOtp.setVerified(true);
        otpRepo.save(serviceOtp);

        return true;
    }
}