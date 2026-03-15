package com.campaign.demo.emerald_user.controller;

import com.campaign.demo.emerald_user.dto.AccountResponse;
import com.campaign.demo.emerald_user.dto.AccountSeedRequest;
import com.campaign.demo.emerald_user.service.EmeraldService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class EmeraldController {

    private final EmeraldService emeraldService;

    public EmeraldController(EmeraldService emeraldService) {
        this.emeraldService = emeraldService;
    }

    @GetMapping("/")
    public ResponseEntity<AccountResponse> getAccount() {
        return ResponseEntity.ok(emeraldService.getAccount());
    }

    @PostMapping("/seed")
    public ResponseEntity<AccountResponse> seedAccount(@Valid @RequestBody AccountSeedRequest request){
        return ResponseEntity.ok(emeraldService.seedAccount(request));
    }
}