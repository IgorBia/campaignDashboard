package com.campaign.demo.emerald_user.service;

import com.campaign.demo.emerald_user.dto.AccountResponse;
import com.campaign.demo.emerald_user.dto.AccountSeedRequest;
import com.campaign.demo.emerald_user.mapper.EmeraldAccountMapper;
import com.campaign.demo.emerald_user.model.EmeraldAccount;
import com.campaign.demo.emerald_user.repository.EmeraldAccountRepository;
import com.campaign.demo.utility.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EmeraldService {

    private final EmeraldAccountRepository emeraldAccountRepository;
    private final EmeraldAccountMapper emeraldAccountMapper;

    public EmeraldService(EmeraldAccountRepository emeraldAccountRepository, EmeraldAccountMapper emeraldAccountMapper) {
        this.emeraldAccountRepository = emeraldAccountRepository;
        this.emeraldAccountMapper = emeraldAccountMapper;
    }

    public AccountResponse getAccount() {
        return emeraldAccountRepository.findAll().stream()
            .findFirst()
            .map(emeraldAccountMapper::toResponse)
            .orElseThrow(() -> new NotFoundException("No account found"));
    }

    public EmeraldAccount getAccountEntity() {
        return emeraldAccountRepository.findAll().stream()
            .findFirst()
            .orElseThrow(() -> new NotFoundException("No account found"));
    }

    public AccountResponse seedAccount(AccountSeedRequest request) {
        EmeraldAccount account = getAccountEntity();
        account.setBalance(request.balance());
        return emeraldAccountMapper.toResponse(emeraldAccountRepository.save(account));
    }
}
