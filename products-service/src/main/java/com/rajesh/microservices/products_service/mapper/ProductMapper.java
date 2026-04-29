package com.rajesh.microservices.products_service.mapper;

import com.rajesh.microservices.products_service.dto.ProductRequestDTO;
import com.rajesh.microservices.products_service.dto.ProductResponseDTO;
import com.rajesh.microservices.products_service.entity.Product;

public class ProductMapper {

    public static Product toEntity(ProductRequestDTO dto){
        return Product.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .quantity(dto.getQuantity())
                .status("Available")
                .build();
    }

    public static ProductResponseDTO toDto(Product product){
        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .status(product.getStatus())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}
