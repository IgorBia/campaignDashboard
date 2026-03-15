package com.campaign.demo.emerald_user.repository;

import com.campaign.demo.emerald_user.model.EmeraldAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmeraldAccountRepository extends JpaRepository<EmeraldAccount, Long> {
}
