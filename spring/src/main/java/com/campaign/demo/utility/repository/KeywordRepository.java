package com.campaign.demo.utility.repository;

import com.campaign.demo.utility.model.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface KeywordRepository extends JpaRepository<Keyword, UUID> {
	List<Keyword> findAllByOrderByValueAsc();

	List<Keyword> findByValueStartingWithIgnoreCaseOrderByValueAsc(
		String query
	);
}
