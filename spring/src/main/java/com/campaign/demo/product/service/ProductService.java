package com.campaign.demo.product.service;

import com.campaign.demo.emerald_user.service.EmeraldService;
import com.campaign.demo.product.dto.ProductCreateRequest;
import com.campaign.demo.product.dto.ProductResponse;
import com.campaign.demo.product.dto.ProductUpdateRequest;
import com.campaign.demo.product.mapper.ProductMapper;
import com.campaign.demo.product.model.Product;
import com.campaign.demo.product.repository.ProductRepository;
import com.campaign.demo.utility.exception.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final EmeraldService emeraldService;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper,
                          EmeraldService emeraldService) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.emeraldService = emeraldService;
    }

    public List<ProductResponse> getAllProducts() {
        return productMapper.toResponseList(
                productRepository.findAllByEmeraldAccountId(emeraldService.getAccountEntity().getId()));
    }

    @Transactional
    public ProductResponse createProduct(ProductCreateRequest request) {
        Product product = new Product();
        product.setName(request.name());
        product.setEmeraldAccount(emeraldService.getAccountEntity());
        return productMapper.toResponse(productRepository.save(product));
    }

    @Transactional
    public ProductResponse updateProduct(UUID id, ProductUpdateRequest request) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Product not found"));
        product.setName(request.name());
        return productMapper.toResponse(productRepository.save(product));
    }

    @Transactional
    public void deleteProduct(UUID id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Product not found"));
        productRepository.delete(product);
    }
}
