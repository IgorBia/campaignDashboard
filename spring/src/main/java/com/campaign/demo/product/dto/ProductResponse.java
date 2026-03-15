package com.campaign.demo.product.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProductResponse(
    UUID id,
    String name,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}