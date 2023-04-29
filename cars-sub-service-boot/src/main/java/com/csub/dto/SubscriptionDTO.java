package com.csub.dto;

public record SubscriptionDTO(long id, boolean is_active, String start_date, int month_price, int total_price, int total_months,
                              long user_id, long car_id, long manager_id, String status) {
}
