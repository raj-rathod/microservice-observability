package com.rajesh.microservices.order.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderRequestDTO {

    @NotNull
    private Long productId;

    @NotNull
    private Integer quantity;

}