package com.fixOnGo.backend.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Worker")
public class Worker {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer worker_Id;

	@Column(nullable = false, name = "AadharNO", unique = true)
	private String worker_AadharNo;

	@Column(nullable = false, name = "PanNO", unique = true)
	private String worker_PanNo;

	@Column(nullable = false, length = 10)
	private String worker_phoneno;

	@Column(nullable = false)
	private int worker_age;

	@Column(nullable = false)
	private String worker_name;

	@Column(name = "gender")
	private String worker_gender;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String street_address;

	@Column(nullable = false)
	private String state;
	
	@Column(nullable = false)
	private String district;
	
	@Column(nullable = false)
	private String city;
	
	@Column(nullable = false)
	private int pincode;
	

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Role role;

	@Column(name = "worker_profile_img")
	private String worker_profile_img;

	// @Column(nullable= false)
	private String worker_complaint_img;

	// @Column(nullable = false)
	private String worker_Aadhar_img;

	// @Column(nullable=false)
	private String worker_Pan_img;

	@Column
	private String resetToken;

	@Column
	private LocalDateTime resetTokenExpiry;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private WorkerRequestStatus workerRequestStatus;

	private int worker_experience;

	@Enumerated(EnumType.STRING)
	private ServiceCategory serviceCategory;

	private String worker_bio;

	private String worker_languages;

	private String worker_certificates;

	private String worker_education;
	
	private double worker_fees;
		
	private String worker_status;

	@JsonIgnore
	@OneToMany(mappedBy = "worker", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ServicesHistory> servicesHistory;

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "worker_services", joinColumns = @JoinColumn(name = "worker_Id"), inverseJoinColumns = @JoinColumn(name = "service_Id"))
	private List<Services> services;
	
	@JsonIgnore
	@OneToMany(mappedBy = "worker", cascade = CascadeType.ALL)
	private List<Feedback> feedbacks;
}
