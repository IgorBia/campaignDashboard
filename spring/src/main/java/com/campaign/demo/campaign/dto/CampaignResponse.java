package com.campaign.demo.campaign.dto;

import com.campaign.demo.campaign.model.CampaignStatus;
import com.campaign.demo.utility.dto.KeywordResponse;
import com.campaign.demo.utility.dto.TownResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record CampaignResponse(
    UUID id,
    String name,
    UUID productId,
    String productName,
    TownResponse town,
    BigDecimal bidAmount,
    List<KeywordResponse> keywords,
    BigDecimal campaignFund,
    CampaignStatus status,
    int radiusKm,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}