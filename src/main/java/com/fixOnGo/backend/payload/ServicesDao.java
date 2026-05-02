package com.fixOnGo.backend.payload;

import com.fixOnGo.backend.entity.ServiceCategory;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ServicesDao {

	@NotEmpty
	@Size(min = 5, message = "Service Title Must Be Valid")
	private String service_Title;

	@NotEmpty
	@Size(min = 20, message = "Service Description Must In 20 To 100 Charecters")
	private String service_Description;

	@Min(value = 100, message = "Minimum Price is 100")
	private int service_price;

	private ServiceCategory serviceCategory;

	private String service_img;

	@Min(value = 1, message = "Minimun Worker 1")
	private int total_worker;

}
