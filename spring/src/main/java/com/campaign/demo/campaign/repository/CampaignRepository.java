package com.campaign.demo.campaign.repository;

import com.campaign.demo.campaign.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CampaignRepository extends JpaRepository<Campaign, UUID> {
	boolean existsByProductId(UUID productId);
	List<Campaign> findAllByProductEmeraldAccountId(UUID emeraldAccountId);
}
