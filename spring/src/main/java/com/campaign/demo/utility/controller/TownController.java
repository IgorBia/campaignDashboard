package com.campaign.demo.utility.controller;

import com.campaign.demo.utility.dto.TownResponse;
import com.campaign.demo.utility.service.TownService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/towns")
public class TownController {
    private final TownService townService;

    public TownController(TownService townService) {
        this.townService = townService;
    }

    @GetMapping
    public List<TownResponse> getAllTowns() {
        return townService.getAllTowns();
    }
}