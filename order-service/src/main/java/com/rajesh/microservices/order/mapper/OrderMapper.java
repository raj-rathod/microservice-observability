package com.rajesh.microservices.order.mapper;

import com.rajesh.microservices.order.dto.OrderRequestDTO;
import com.rajesh.microservices.order.dto.OrderResponseDTO;
import com.rajesh.microservices.order.entity.Order;

public class OrderMapper {

    // DTO → Entity
    public static Order toEntity(OrderRequestDTO dto, Long userId, Double price) {
        return Order.builder()
                .userId(userId)
                .productId(dto.getProductId())
                .quantity(dto.getQuantity())
                .price(price) // snapshot price
                .status("CREATED")
                .build();
    }

    // Entity → DTO
    public static OrderResponseDTO toDTO(Order order) {
        return OrderResponseDTO.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .productId(order.getProductId())
                .quantity(order.getQuantity())
                .price(order.getPrice())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }
}