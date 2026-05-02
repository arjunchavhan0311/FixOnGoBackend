package com.fixOnGo.backend.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "Admin")
public class Admin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int admin_Id;

	@Column(nullable = false, length = 10)
	private String admin_phoneno;

	@Column(nullable = false)
	private int admin_age;

	@Column(nullable = false)
	private String admin_name;

	@Column(name = "gender")
	private String admin_gender;

	@Column(unique = true)
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

	@Column(name = "admin_profile_img")
	private String admin_profile_img;

	@Column
	private String resetToken;

	@Column
	private LocalDateTime resetTokenExpiry;

}
