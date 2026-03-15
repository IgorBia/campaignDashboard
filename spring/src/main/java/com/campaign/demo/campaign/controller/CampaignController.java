package com.campaign.demo.campaign.controller;

import com.campaign.demo.campaign.dto.CampaignCreateRequest;
import com.campaign.demo.campaign.dto.CampaignResponse;
import com.campaign.demo.campaign.dto.CampaignUpdateRequest;
import com.campaign.demo.campaign.service.CampaignService; 
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/campaigns")
public class CampaignController {
    private final CampaignService campaignService;

    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @GetMapping
    public ResponseEntity<List<CampaignResponse>> getAllCampaigns() {
        return ResponseEntity.ok(campaignService.getAllCampaigns());
    }

    @PostMapping
    public ResponseEntity<CampaignResponse> createCampaign(@Valid @RequestBody CampaignCreateRequest request) {
        return ResponseEntity.ok(campaignService.createCampaign(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CampaignResponse> getCampaignById(@PathVariable UUID id) {
        return ResponseEntity.ok(campaignService.getCampaignById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CampaignResponse> updateCampaign(@PathVariable UUID id, @Valid @RequestBody CampaignUpdateRequest request) {
        return ResponseEntity.ok(campaignService.updateCampaign(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCampaign(@PathVariable UUID id) {
        campaignService.deleteCampaign(id);
        return ResponseEntity.ok().build();
    }
}