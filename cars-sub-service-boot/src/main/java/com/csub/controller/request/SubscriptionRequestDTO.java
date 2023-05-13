package com.csub.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionRequestDTO {

    @NotBlank(message = "Month price cannot be blank")
    private String monthPrice;

    @NotBlank(message = "Total month cannot be blank")
    private String totalMonths;

    @NotBlank(message = "User cannot be blank")
    private String userId;

    @NotBlank(message = "Car cannot be blank")
    private String carId;

    @NotBlank(message = "Passport number cannot be blank")
    private String passportNumber;

    @NotBlank(message = "IPN number cannot be blank")
    private String ipnNumber;

    @NotBlank(message = "Social media link cannot be blank")
    private String socMediaLink;
}