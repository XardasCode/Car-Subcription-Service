package com.csub.controller.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarRequestDTO {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Model cannot be blank")
    private String model;

    @NotBlank(message = "Brand cannot be blank")
    private String brand;

    @NotBlank(message = "Year cannot be blank")
    @Size(min = 4, max = 4,message = "Year must be 4 characters")
    private String year;

    @NotBlank(message = "Color cannot be blank")
    private String color;

    @NotBlank(message = "Price cannot be blank")
    private String price;

    @NotBlank(message = "Fuel type cannot be blank")
    private String fuelType;

    @NotBlank(message = "Chassis number type cannot be blank")
    @Size(min = 17, max = 17,message = "Chassis number must be 17 characters")
    private String chassisNumber;

    @NotBlank(message = "Register number cannot be blank")
    private String regNumber;

    @NotBlank(message = "Register date cannot be blank")
    private String regDate;

    @NotBlank(message = "Mileage cannot be blank")
    private String mileage;

    @NotBlank(message = "Last service date cannot be blank")
    private String lastServiceDate;

    @NotBlank(message = "Status id cannot be blank")
    private String statusId;

    @NotBlank(message = "Image cannot be blank")
    private String image;
}
