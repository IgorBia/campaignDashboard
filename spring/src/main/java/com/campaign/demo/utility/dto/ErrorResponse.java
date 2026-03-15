package com.campaign.demo.utility.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
    String code,
    String message,
    List<String> details,
    LocalDateTime timestamp
) {
}