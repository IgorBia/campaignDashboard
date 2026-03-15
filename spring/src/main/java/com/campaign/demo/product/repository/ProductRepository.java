package com.campaign.demo.product.repository;

import com.campaign.demo.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    List<Product> findAllByEmeraldAccountId(UUID emeraldAccountId);
}
