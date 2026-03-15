package com.campaign.demo.utility.controller;

import com.campaign.demo.utility.dto.KeywordResponse;
import com.campaign.demo.utility.model.Keyword;
import com.campaign.demo.utility.repository.KeywordRepository;
import com.querydsl.core.types.Predicate;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/keywords")
public class KeywordController {

    private final KeywordRepository keywordRepository;

    public KeywordController(KeywordRepository keywordRepository) {
        this.keywordRepository = keywordRepository;
    }

    // find all that start with a certain string, or contain a certain string, or end with a certain string
    @GetMapping
    public List<KeywordResponse> getAllKeywords(@QuerydslPredicate(root = Keyword.class) Predicate predicate) {
        return keywordRepository.findAll(predicate).stream()
            .map(keyword -> new KeywordResponse(keyword.getId(), keyword.getValue()))
            .toList();
    }
}