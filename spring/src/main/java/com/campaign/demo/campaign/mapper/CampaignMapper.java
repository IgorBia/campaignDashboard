package com.campaign.demo.campaign.mapper;

import com.campaign.demo.campaign.dto.CampaignDeleteResponse;
import com.campaign.demo.campaign.dto.CampaignMutationResponse;
import com.campaign.demo.campaign.dto.CampaignResponse;
import com.campaign.demo.campaign.model.Campaign;
import com.campaign.demo.utility.mapper.KeywordMapper;
import com.campaign.demo.utility.mapper.TownMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", uses = {TownMapper.class, KeywordMapper.class})
public interface CampaignMapper {

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    CampaignResponse toResponse(Campaign campaign);

    List<CampaignResponse> toResponseList(List<Campaign> campaigns);

    default CampaignMutationResponse toMutationResponse(Campaign campaign, BigDecimal accountBalance) {
        return new CampaignMutationResponse(toResponse(campaign), accountBalance);
    }

    default CampaignDeleteResponse toDeleteResponse(UUID deletedCampaignId, BigDecimal accountBalance) {
        return new CampaignDeleteResponse(deletedCampaignId, accountBalance);
    }
}
