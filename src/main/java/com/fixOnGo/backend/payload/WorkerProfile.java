package com.fixOnGo.backend.payload;

import com.fixOnGo.backend.entity.ServiceCategory;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class WorkerProfile {

	@NotEmpty
	@Size(min = 5, message = "Worker Name Must Be More Than 5 Charecters...")
	private String worker_name;

	@NotEmpty
	private String worker_gender;

	@Min(value = 18, message = "Worker Must Be More Than 18 Year Old...")
	private int worker_age;

	@NotEmpty
	@Size(min = 5, message = "Office Address Must Required...")
	private String street_address;

	@NotEmpty
	@Size(min = 5, message = "State Must Required...")
	private String state;

	@NotEmpty
	@Size(min = 5, message = "District Must Required...")
	private String district;
	
	@NotEmpty
	@Size(min = 5, message = "City Must Required...")
	private String city;

	@Min(value = 6, message = "PinCode Must Be 6 Digit...")
	private int pincode;

	@Pattern(regexp = "^[6-9][0-9]{9}$", message = "Phone number must be a valid 10-digit Indian mobile number")
	private String worker_phoneno;

	private int worker_experience;

	private ServiceCategory serviceCategory;

	private String worker_bio;

	private String worker_languages;

	private String worker_certificates;

	private String worker_education;
	
	@NotNull
	@Min(value = 100, message = "Minimum Price is 100")
	private double worker_fees;

	private String worker_status;
}
