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

    @NotBlank(message = "isActive cannot be blank")
    private String isActive;

    @NotBlank(message = "Start date cannot be blank")
    private String startDate;

    @NotBlank(message = "Month price cannot be blank")
    private String monthPrice;

    @NotBlank(message = "Total price cannot be blank")
    private String totalPrice;

    @NotBlank(message = "Total month cannot be blank")
    private String totalMonths;

    @NotBlank(message = "User cannot be blank")
    private String userId;

    @NotBlank(message = "Car cannot be blank")
    private String carId;

    @NotBlank(message = "Manager cannot be blank")
    private String managerId;

    @NotBlank(message = "Status cannot be blank")
    private String statusId;
}