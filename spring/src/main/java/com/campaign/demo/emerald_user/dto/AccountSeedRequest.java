package com.campaign.demo.emerald_user.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AccountSeedRequest(
    @NotNull @DecimalMin("0.00") BigDecimal balance
) {
}