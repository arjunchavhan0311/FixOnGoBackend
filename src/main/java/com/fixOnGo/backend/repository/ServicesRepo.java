package com.fixOnGo.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fixOnGo.backend.entity.Services;

public interface ServicesRepo extends JpaRepository<Services, Integer> {

}
