package com.fixOnGo.backend.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "Customer")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer customer_Id;

	@Column(nullable = false, length = 10)
	private String customer_phoneno;

	@Column(nullable = false)
	private int customer_age;

	@Column(nullable = false)
	private String customer_name;

	@Column(name = "gender")
	private String customer_gender;

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
	
	@Column(name = "customer_office_address")
	private String customer_office_address;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Role role;

	@Column(name = "customer_profile_img")
	private String customer_profile_img;

	@Column
	private String resetToken;

	@Column
	private LocalDateTime resetTokenExpiry;

	@JsonIgnore
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ServicesHistory> servicesHistory;
	
	@JsonIgnore
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	private List<Feedback> feedbacks;
	
	
	
	
}