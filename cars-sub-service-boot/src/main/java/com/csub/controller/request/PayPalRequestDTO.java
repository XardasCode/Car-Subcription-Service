package com.csub.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayPalRequestDTO {

    @NotBlank(message = "Price cannot be blank")
    private String price;

    @NotBlank(message = "Currency cannot be blank")
    private String currency;

    @NotBlank(message = "Method cannot be blank")
    private String method;

    @NotBlank(message = "Intent cannot be blank")
    private  String intent;

    @NotBlank(message = "Description cannot be blank")
    private String description;
}
