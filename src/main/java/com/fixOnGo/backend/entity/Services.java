package com.fixOnGo.backend.entity;

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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Services {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer service_Id;

	@Column(nullable = false)
	private String service_Title;

	@Column(nullable = false)
	private String service_Description;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ServiceCategory serviceCategory;

	private String service_img;

	@Column(nullable = false)
	private int service_price;

	private int service_rating;

	private int total_worker;

	@JsonIgnore
	@OneToMany(mappedBy = "services", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ServicesHistory> servicesHistory;
	
	@JsonIgnore
	@OneToMany(mappedBy = "services", cascade = CascadeType.ALL)
	private List<Feedback> feedbacks;
	
}
