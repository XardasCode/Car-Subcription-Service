package com.csub.dto;

public record UserDTO(long id, String name, String surname, String email, String phone, boolean isBlocked,
                      boolean isVerified, int subscriptionId, String role) {
}
