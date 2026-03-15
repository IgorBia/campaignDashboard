package com.campaign.demo.campaign.repository;

import com.campaign.demo.campaign.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
}
