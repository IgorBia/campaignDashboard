package com.campaign.demo.utility.service;

import com.campaign.demo.utility.dto.KeywordResponse;
import com.campaign.demo.utility.mapper.KeywordMapper;
import com.campaign.demo.utility.repository.KeywordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeywordService {

    private final KeywordRepository keywordRepository;
    private final KeywordMapper keywordMapper;

    public KeywordService(KeywordRepository keywordRepository, KeywordMapper keywordMapper) {
        this.keywordRepository = keywordRepository;
        this.keywordMapper = keywordMapper;
    }

    public List<KeywordResponse> getAllKeywords(String query) {
        String normalizedQuery = query == null ? "" : query.trim();

        if (normalizedQuery.isEmpty()) {
            return keywordMapper.toResponseList(keywordRepository.findAllByOrderByValueAsc());
        }

        return keywordMapper.toResponseList(
            keywordRepository.findByValueStartingWithIgnoreCaseOrderByValueAsc(normalizedQuery)
        );
    }
}
