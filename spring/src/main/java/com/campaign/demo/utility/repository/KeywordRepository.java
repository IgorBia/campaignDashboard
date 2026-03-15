package com.campaign.demo.utility.repository;

import com.campaign.demo.utility.model.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
}
