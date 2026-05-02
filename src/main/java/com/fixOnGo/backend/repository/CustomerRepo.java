package com.fixOnGo.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fixOnGo.backend.entity.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {

	Customer findByEmail(String email);

}
