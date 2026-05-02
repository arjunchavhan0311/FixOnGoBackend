package com.fixOnGo.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fixOnGo.backend.entity.Admin;

public interface AdminRepo extends JpaRepository<Admin, Integer> {

	Admin findByEmail(String email);

}
