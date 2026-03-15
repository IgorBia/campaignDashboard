package com.campaign.demo.utility.controller;

import com.campaign.demo.utility.dto.KeywordResponse;
import com.campaign.demo.utility.repository.KeywordRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/keywords")
public class KeywordController {

    private final KeywordRepository keywordRepository;

    public KeywordController(KeywordRepository keywordRepository) {
        this.keywordRepository = keywordRepository;
    }

    @GetMapping
    public List<KeywordResponse> getAllKeywords(@RequestParam(required = false, defaultValue = "") String query) {
        String normalizedQuery = query.trim();

        if (normalizedQuery.isEmpty()) {
            return keywordRepository.findAllByOrderByValueAsc().stream()
                .map(keyword -> new KeywordResponse(keyword.getId(), keyword.getValue()))
                .toList();
        }

        return keywordRepository
            .findByValueStartingWithIgnoreCaseOrderByValueAsc(normalizedQuery)
            .stream()
            .map(keyword -> new KeywordResponse(keyword.getId(), keyword.getValue()))
            .toList();
    }
}