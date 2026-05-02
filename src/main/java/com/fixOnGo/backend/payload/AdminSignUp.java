package com.fixOnGo.backend.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AdminSignUp {

	@NotEmpty
	@Size(min = 4, message = "Name Must Be More Than 4 Charecter")
	private String admin_name;

	private String admin_gender;

	@Min(value = 18, message = "Age Must Be More Than 17 Year")
	private int admin_age;

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

	@Email(message = "Email Must Be Unique and Valid")
	@NotEmpty
	@Size(min = 4, message = "Name Must Be More Than 4 Charecter")
	private String email;

	@Size(min = 8, max = 10, message = "Password Must Be More Than 7 and Less Than 10 Chrecters")
	@Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,10}$", message = "Password must contain uppercase, lowercase, number, special character and be 8–10 characters long")
	private String password;

}
