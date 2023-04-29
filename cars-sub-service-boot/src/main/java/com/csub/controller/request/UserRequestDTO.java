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
public class UserRequestDTO {

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters")
    private String name;

    @NotBlank(message = "Surname cannot be blank")
    @Size(min = 2, max = 30, message = "Surname must be between 2 and 30 characters")
    private String surname;

    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotBlank(message = "Phone cannot be blank")
    private String phone;

    @NotBlank(message = "Password cannot be blank")
    private String password;
}

