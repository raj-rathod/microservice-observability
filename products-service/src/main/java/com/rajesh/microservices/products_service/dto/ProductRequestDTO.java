package com.rajesh.microservices.products_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductRequestDTO {
    @NotNull
    private String name;

    @NotNull
    private Double price;

    @NotNull
    private Integer quantity;
}
