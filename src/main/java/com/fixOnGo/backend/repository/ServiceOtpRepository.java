package com.fixOnGo.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fixOnGo.backend.entity.ServiceOTP;

public interface ServiceOtpRepository extends JpaRepository<ServiceOTP, Integer> {
    Optional<ServiceOTP> findByServiceId(int serviceId);
}
