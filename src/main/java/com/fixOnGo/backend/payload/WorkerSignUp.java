package com.fixOnGo.backend.payload;

import com.fixOnGo.backend.entity.ServiceCategory;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class WorkerSignUp {

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

	@NotEmpty
	@Size(min = 5, message = "Worker Name Must Be More Than 5 Charecters...")
	@Email(message = "Worker Email Id Must Required and  Unique....")
	private String email;

	@Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,10}$", message = "Password must contain uppercase, lowercase, number, special character and be 8–10 characters long")
	@Size(min = 8, max = 10, message = "Password Must Be More Than 7 and Less Than 10 Chrecters")
	private String password;

	@Pattern(regexp = "^[2-9][0-9]{11}$", message = "Aadhaar number must be a valid 12-digit number and should not start with 0")
	private String worker_AadharNo;

	@Pattern(regexp = "^[A-Z]{5}[0-9]{4}[A-Z]$", message = "PAN number must be in format ABCDE1234F")
	private String worker_PanNo;

	@Pattern(regexp = "^[6-9][0-9]{9}$", message = "Phone number must be a valid 10-digit Indian mobile number")
	private String worker_phoneno;

	private String worker_Aadhar_img;

	private String worker_Pan_img;

	private int worker_experience;

	private ServiceCategory serviceCategory;

	private String worker_bio;

	private String worker_languages;

	private String worker_certificates;

	private String worker_education;

	private String worker_status;

}
