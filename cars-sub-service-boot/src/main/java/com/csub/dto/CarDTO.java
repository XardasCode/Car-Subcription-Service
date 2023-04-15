package com.csub.dto;

public record CarDTO(long id, String name, String model, String brand, int year, String color, int price,
                     String fuelType, String chassisNumber, String regNumber, String regDate, String mileage,
                     String lastServiceDate, String status) {
}