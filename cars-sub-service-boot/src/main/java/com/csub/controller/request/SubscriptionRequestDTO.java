package com.csub.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
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
    private String user_id;

    @NotBlank(message = "Car cannot be blank")
    private String car_id;

    @NotBlank(message = "Manager cannot be blank")
    private String manager_id;

    @NotBlank(message = "Status cannot be blank")
    private String status_id;
}