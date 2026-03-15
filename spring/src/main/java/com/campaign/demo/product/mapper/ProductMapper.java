package com.campaign.demo.product.mapper;

import com.campaign.demo.product.dto.ProductResponse;
import com.campaign.demo.product.model.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductResponse toResponse(Product product);

    List<ProductResponse> toResponseList(List<Product> products);
}
