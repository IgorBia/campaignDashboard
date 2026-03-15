package com.campaign.demo.campaign.dto;

import com.campaign.demo.campaign.model.CampaignStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public record CampaignUpdateRequest(
    @NotNull UUID productId,
    @NotBlank @Size(max = 120) String name,
    @NotEmpty Set<UUID> keywordIds,
    @NotNull @DecimalMin(value = "0.01", inclusive = true, message = "Bid amount must be at least 0.01") BigDecimal bidAmount,
    @NotNull @DecimalMin(value = "0.00", inclusive = false, message = "Campaign fund must be greater than 0") BigDecimal campaignFund,
    @NotNull CampaignStatus status,
    @NotNull UUID townId,
    @Min(1) int radiusKm
) {
}