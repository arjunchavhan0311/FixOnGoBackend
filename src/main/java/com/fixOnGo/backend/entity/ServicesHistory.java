package com.fixOnGo.backend.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ServicesHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer historyId;

	@Column(nullable = false)
	private long customer_available_phoneno;

	@Column(nullable = false)
	private String customer_Issues;

	private String issue_evidence_img;

	@Column(nullable = false)
	private String service_location;

	@Column(nullable = false)
	private LocalDate booking_date;

	@Column(nullable = false)
	private LocalTime booking_time;

	private LocalDate completion_date;

	private LocalTime completion_time;

	@Column(nullable = false)
	private LocalDate available_date;

	@Column(nullable = false)
	private LocalTime available_time;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ServiceStatus serviceStatus;

	@Enumerated(EnumType.STRING)
	private PaymentType paymentType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_Id")
	private Customer customer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "worker_Id")
	private Worker worker;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "service_Id")
	private Services services;

//	// Later Add Feedback as Join Table
//	@OneToOne(mappedBy = "servicesHistory", cascade = CascadeType.ALL)
//	private Feedback feedback;
	
}
