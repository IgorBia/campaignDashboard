package com.campaign.demo.utility.controller;

import com.campaign.demo.utility.dto.KeywordResponse;
import com.campaign.demo.utility.service.KeywordService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dictionaries/keywords")
public class KeywordController {

    private final KeywordService keywordService;

    public KeywordController(KeywordService keywordService) {
        this.keywordService = keywordService;
    }

    @GetMapping
    public List<KeywordResponse> getAllKeywords(@RequestParam(required = false, defaultValue = "") String query) {
        return keywordService.getAllKeywords(query);
    }
}