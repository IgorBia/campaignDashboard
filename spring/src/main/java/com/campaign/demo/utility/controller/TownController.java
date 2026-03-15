package com.campaign.demo.utility.controller;

import com.campaign.demo.utility.dto.TownResponse;
import com.campaign.demo.utility.repository.TownRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/towns")
public class TownController {
    private final TownRepository townRepository;

    public TownController(TownRepository townRepository) {
        this.townRepository = townRepository;
    }

    @GetMapping
    public List<TownResponse> getAllTowns() {
        return townRepository.findAll().stream()
            .map(town -> new TownResponse(town.getId(), town.getName()))
            .toList();
    }
}