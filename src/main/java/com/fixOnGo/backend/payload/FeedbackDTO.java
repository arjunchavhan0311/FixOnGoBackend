package com.fixOnGo.backend.payload;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FeedbackDTO {

    @NotEmpty
    @Size(min = 5, message = "Description must be at least 5 characters")
    private String description;

    @NotNull
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be maximum 5")
    private int rating;

    @NotNull
    private Integer historyId;

    @NotNull
    private Integer customerId;

    @NotNull
    private Integer workerId;

    @NotNull
    private Integer serviceId;
}