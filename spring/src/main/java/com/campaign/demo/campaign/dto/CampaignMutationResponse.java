package com.campaign.demo.campaign.dto;

import java.math.BigDecimal;

public record CampaignMutationResponse(
	CampaignResponse campaign,
	BigDecimal accountBalance
) {
}
