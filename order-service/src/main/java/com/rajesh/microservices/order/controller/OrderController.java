package com.rajesh.microservices.order.controller;

import com.rajesh.microservices.order.dto.OrderRequestDTO;
import com.rajesh.microservices.order.dto.OrderResponseDTO;
import com.rajesh.microservices.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    // CREATE
    @PostMapping
    public OrderResponseDTO create(
            @RequestBody OrderRequestDTO dto) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("Create Order: {}", dto.getProductId());
        return service.create(dto, Long.valueOf(userId));
    }

    // GET ALL
    @GetMapping
    public List<OrderResponseDTO> getAll() {
        return service.getAll();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public OrderResponseDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public OrderResponseDTO update(
            @PathVariable Long id,
            @RequestBody OrderRequestDTO dto) {

        return service.update(id, dto);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Order deleted successfully";
    }

    @GetMapping("/my")
    public List<OrderResponseDTO> getMyOrders(@RequestHeader Map<String, String> headers) {
        log.info("Entering Order Controller - getMyOrders");
        headers.forEach((k, v) -> System.out.println(k + " = " + v));
        return service.getMyOrders();
    }
}