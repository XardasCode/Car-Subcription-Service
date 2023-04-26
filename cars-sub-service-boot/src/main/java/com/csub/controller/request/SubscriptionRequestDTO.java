package com.csub.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class SubscriptionRequestDTO {
    @NotBlank(message = "isActive cannot be blank")
    private boolean isActive;
    @NotBlank(message = "Start date cannot be blank")
    private String startDate;
    @NotBlank(message = "Month price cannot be blank")
    @Positive(message = "Month price must be positive")
    private int monthPrice;
    @NotBlank(message = "Total price cannot be blank")
    @Positive(message = "Total price must be positive")
    private int totalPrice;
    @NotBlank(message = "Total month cannot be blank")
    @Positive(message = "Total month must be positive")
    private int totalMonths;
    @NotBlank(message = "User cannot be blank")
    private int user_id;
    @NotBlank(message = "Car cannot be blank")
    private int car_id;
    @NotBlank(message = "Manager cannot be blank")
    private int manager_id;
    @NotBlank(message = "Status cannot be blank")
    private int status_id;




}
