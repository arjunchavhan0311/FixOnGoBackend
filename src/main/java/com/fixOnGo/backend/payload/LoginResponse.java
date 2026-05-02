package com.fixOnGo.backend.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {

	private int id;
	private String token;
	private String email;
	private String role;
	private String city;

}
