package com.fixOnGo.backend.payload;

import lombok.Data;

@Data
public class ResetOfficerPasswordRequest {
	private String officerEmail;
	private String newPassword;
}
