package com.rajesh.microservices.products_service.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ProductResponseDTO {
    private Long id;
    private String name;
    private Integer quantity;
    private Double price;
    private String status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
