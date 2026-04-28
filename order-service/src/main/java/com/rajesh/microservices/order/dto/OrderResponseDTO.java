package com.rajesh.microservices.order.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OrderResponseDTO {

    private Long id;
    private Long userId;
    private Long productId;
    private Integer quantity;
    private Double price;
    private String status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}