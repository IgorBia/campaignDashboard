package com.campaign.demo.utility.repository;

import com.campaign.demo.utility.model.Town;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TownRepository extends JpaRepository<Town, Long> {
}
