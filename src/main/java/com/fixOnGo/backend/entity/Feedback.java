package com.fixOnGo.backend.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name="Feedback")
public class Feedback {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int feedback_id;

	@Column(name = "Description", nullable = false)
	private String description;
	
	@Column(name="Rating", nullable = false)
	private int rating;
	
	private LocalDate feedback_date;
	
	private LocalTime feedback_time;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "service_Id")
	private Services services;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "worker_Id")
	private Worker worker;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_Id")
	private Customer customer;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "history_Id")
	private ServicesHistory servicesHistory;
}
