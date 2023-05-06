package com.csub.dto;

public record SubscriptionDTO(long id, boolean isActive, String startDate, int monthPrice, int totalPrice, int totalMonths,
                              long userId, long carId, long managerId, String status) {
}
