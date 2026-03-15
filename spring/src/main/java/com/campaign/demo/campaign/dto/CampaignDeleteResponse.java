package com.campaign.demo.campaign.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record CampaignDeleteResponse(
	UUID deletedCampaignId,
	BigDecimal accountBalance
) {
}
