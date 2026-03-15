package com.campaign.demo.emerald_user.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "emerald_account")
public class EmeraldAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotNull
    @DecimalMin("0.00")
    @Column(name = "balance", nullable = false, precision = 19, scale = 2)
    private BigDecimal balance;

    @NotBlank
    @Column(name = "currency", nullable = false, length = 20)
    private String currency;
}
