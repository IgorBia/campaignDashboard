package com.campaign.demo.campaign.service;

import com.campaign.demo.campaign.dto.CampaignCreateRequest;
import com.campaign.demo.campaign.dto.CampaignDeleteResponse;
import com.campaign.demo.campaign.dto.CampaignMutationResponse;
import com.campaign.demo.campaign.dto.CampaignResponse;
import com.campaign.demo.campaign.dto.CampaignUpdateRequest;
import com.campaign.demo.campaign.mapper.CampaignMapper;
import com.campaign.demo.campaign.model.Campaign;
import com.campaign.demo.campaign.repository.CampaignRepository;
import com.campaign.demo.emerald_user.model.EmeraldAccount;
import com.campaign.demo.emerald_user.repository.EmeraldAccountRepository;
import com.campaign.demo.emerald_user.service.EmeraldService;
import com.campaign.demo.product.model.Product;
import com.campaign.demo.product.repository.ProductRepository;
import com.campaign.demo.utility.model.Keyword;
import com.campaign.demo.utility.model.Town;
import com.campaign.demo.utility.exception.BusinessConflictException;
import com.campaign.demo.utility.exception.NotFoundException;
import com.campaign.demo.utility.repository.KeywordRepository;
import com.campaign.demo.utility.repository.TownRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class CampaignService {

    private final CampaignRepository campaignRepository;
    private final CampaignMapper campaignMapper;
    private final EmeraldService emeraldService;
    private final EmeraldAccountRepository emeraldAccountRepository;
    private final ProductRepository productRepository;
    private final TownRepository townRepository;
    private final KeywordRepository keywordRepository;

    public CampaignService(CampaignRepository campaignRepository, CampaignMapper campaignMapper,
                           EmeraldService emeraldService, EmeraldAccountRepository emeraldAccountRepository,
                           ProductRepository productRepository, TownRepository townRepository,
                           KeywordRepository keywordRepository) {
        this.campaignRepository = campaignRepository;
        this.campaignMapper = campaignMapper;
        this.emeraldService = emeraldService;
        this.emeraldAccountRepository = emeraldAccountRepository;
        this.productRepository = productRepository;
        this.townRepository = townRepository;
        this.keywordRepository = keywordRepository;
    }

    public List<CampaignResponse> getAllCampaigns() {
        return campaignMapper.toResponseList(
                campaignRepository.findAllByProductEmeraldAccountId(emeraldService.getAccountEntity().getId())
        );
    }

    @Transactional
    public CampaignMutationResponse createCampaign(CampaignCreateRequest request) {
        EmeraldAccount account = emeraldService.getAccountEntity();

        if (account.getBalance().compareTo(request.campaignFund()) < 0) {
            throw new BusinessConflictException("Insufficient account balance");
        }

        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new NotFoundException("Product not found"));

        Town town = townRepository.findById(request.townId())
                .orElseThrow(() -> new NotFoundException("Town not found"));

        Set<Keyword> keywords = new HashSet<>(keywordRepository.findAllById(request.keywordIds()));
        if (keywords.size() != request.keywordIds().size()) {
            throw new NotFoundException("One or more keywords not found");
        }

        Campaign campaign = new Campaign();
        campaign.setName(request.name());
        campaign.setBidAmount(request.bidAmount());
        campaign.setCampaignFund(request.campaignFund());
        campaign.setStatus(request.status());
        campaign.setRadiusKm(request.radiusKm());
        campaign.setProduct(product);
        campaign.setTown(town);
        campaign.setKeywords(keywords);

        account.setBalance(account.getBalance().subtract(request.campaignFund()));
        emeraldAccountRepository.save(account);

        Campaign saved = campaignRepository.save(campaign);
        return campaignMapper.toMutationResponse(saved, account.getBalance());
    }

    public CampaignResponse getCampaignById(UUID id) {
        return campaignMapper.toResponse(campaignRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Campaign not found")));
    }

    @Transactional
    public CampaignMutationResponse updateCampaign(UUID id, CampaignUpdateRequest request) {
        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Campaign not found"));

        EmeraldAccount account = emeraldService.getAccountEntity();

        BigDecimal changeInFund = request.campaignFund().subtract(campaign.getCampaignFund());

        if (account.getBalance().compareTo(changeInFund) < 0) {
            throw new BusinessConflictException("Insufficient account balance");
        }

        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new NotFoundException("Product not found"));

        Town town = townRepository.findById(request.townId())
                .orElseThrow(() -> new NotFoundException("Town not found"));

        Set<Keyword> keywords = new HashSet<>(keywordRepository.findAllById(request.keywordIds()));
        if (keywords.size() != request.keywordIds().size()) {
            throw new NotFoundException("One or more keywords not found");
        }

        campaign.setName(request.name());
        campaign.setBidAmount(request.bidAmount());
        campaign.setCampaignFund(request.campaignFund());
        campaign.setStatus(request.status());
        campaign.setRadiusKm(request.radiusKm());
        campaign.setProduct(product);
        campaign.setTown(town);
        campaign.setKeywords(keywords);

        account.setBalance(account.getBalance().subtract(changeInFund));
        emeraldAccountRepository.save(account);

        Campaign saved = campaignRepository.save(campaign);
        return campaignMapper.toMutationResponse(saved, account.getBalance());
    }

    @Transactional
    public CampaignDeleteResponse deleteCampaign(UUID id) {
        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Campaign not found"));

        EmeraldAccount account = emeraldService.getAccountEntity();
        account.setBalance(account.getBalance().add(campaign.getCampaignFund()));
        emeraldAccountRepository.save(account);

        campaignRepository.delete(campaign);
        return campaignMapper.toDeleteResponse(id, account.getBalance());
    }
}
