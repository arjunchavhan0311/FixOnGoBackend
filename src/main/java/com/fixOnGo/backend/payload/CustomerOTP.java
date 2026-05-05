package com.fixOnGo.backend.payload;

import lombok.Data;

@Data
public class CustomerOTP {
	private String email;
	private String customer_phoneno;
}
