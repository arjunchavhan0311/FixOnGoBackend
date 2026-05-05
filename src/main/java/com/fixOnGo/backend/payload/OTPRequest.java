package com.fixOnGo.backend.payload;

import lombok.Data;

@Data
public class OTPRequest {
	 	
		private int serviceId;
	    private String otp;

}
