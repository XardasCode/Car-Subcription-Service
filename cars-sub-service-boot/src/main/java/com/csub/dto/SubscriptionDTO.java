package com.csub.dto;

public record SubscriptionDTO(long id,
                              boolean isActive,
                              String startDate,
                              int monthPrice,
                              int totalPrice,
                              int totalMonths,
                              String passportNumber,
                              String ipnNumber,
                              String socMediaLink,
                              String lastPayDate,
                              long userId,
                              long carId,
                              long managerId,
                              String status) {
}
