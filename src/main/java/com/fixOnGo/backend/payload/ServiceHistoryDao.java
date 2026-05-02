package com.fixOnGo.backend.payload;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fixOnGo.backend.entity.PaymentStatus;
import com.fixOnGo.backend.entity.PaymentType;
import com.fixOnGo.backend.entity.ServiceStatus;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ServiceHistoryDao {

	@NotNull
	private long customer_available_phoneno;

	@NotEmpty
	@Size(min = 10, message = "Enter Valid Issues")
	private String customer_Issues;

	@NotEmpty
	@Size(min = 10, message = "Enter Correct Location")
	private String service_location;

	private String issue_evidence_img;

	@NotNull
	private LocalDate booking_date;

	@NotNull
	private LocalTime booking_time;

	private LocalDate completion_date;

	private LocalTime completion_time;

	@NotNull
	private LocalDate available_date;

	@NotNull
	private LocalTime available_time;

	@NotNull
	private PaymentStatus paymentStatus;

	@NotNull
	private ServiceStatus serviceStatus;

	@NotNull
	private PaymentType paymentType;

	@NotNull
	private Integer customerId;

	@NotNull
	private Integer workerId;

	@NotNull
	private Integer serviceId;

}
