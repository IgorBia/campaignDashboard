package com.campaign.demo.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProductCreateRequest(
    @NotBlank(message = "Product name must not be blank")
    @Size(min = 1, max = 100, message = "Product name must be between 1 and 100 characters")
    String name
) {
}