package com.campaign.demo.emerald_user.config;

import com.campaign.demo.emerald_user.model.EmeraldAccount;
import com.campaign.demo.emerald_user.repository.EmeraldAccountRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class EmeraldAccountInitializer {

    @Bean
    ApplicationRunner seedEmeraldAccount(
            EmeraldAccountRepository repository,
            @Value("${app.emerald.seed-balance:10000.00}") BigDecimal seedBalance,
            @Value("${app.emerald.currency:USD}") String currency
    ) {
        return args -> {
            if (repository.count() == 0) {
                EmeraldAccount account = new EmeraldAccount();
                account.setBalance(seedBalance);
                account.setCurrency(currency);
                repository.save(account);
            }
        };
    }
}