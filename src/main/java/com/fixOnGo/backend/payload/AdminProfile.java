package com.fixOnGo.backend.payload;

import com.fixOnGo.backend.entity.Role;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AdminProfile {

	@NotEmpty
	@Size(min = 4, message = "Name Must Be More Than 4 Charecter")
	private String admin_name;

	private String admin_gender;

	@Min(value = 18, message = "Age Must Be More Than 17 Year")
	private int admin_age;

	private String email;

	private Role role;

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
	private String admin_phoneno;
	
	private String admin_profile_img;
	

}
