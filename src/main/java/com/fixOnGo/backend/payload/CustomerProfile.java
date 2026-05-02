package com.fixOnGo.backend.payload;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerProfile {

	@NotEmpty
	@Size(min = 5, message = "Name Must Be More Than 5 Charecters")
	private String customer_name;

	@Min(value = 18, message = "Age Must Be More Than 18 Year")
	private int customer_age;

	@NotEmpty
	private String customer_gender;

	@NotEmpty
	@Size(min = 5, message = "Permenent Address Must Required...")
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
	private String customer_phoneno;

	private String customer_office_address;

	private String customer_profile_img;

}
