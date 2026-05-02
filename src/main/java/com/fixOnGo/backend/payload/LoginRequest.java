package com.fixOnGo.backend.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginRequest {

	@Email
	private String email;

	@NotEmpty
	private String password;
}
