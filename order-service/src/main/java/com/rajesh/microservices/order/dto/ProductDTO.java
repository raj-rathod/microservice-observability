package com.rajesh.microservices.order.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ProductDTO {
    private Long id;
    private String name;
    private Integer quantity;
    private Double price;
    private String status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
