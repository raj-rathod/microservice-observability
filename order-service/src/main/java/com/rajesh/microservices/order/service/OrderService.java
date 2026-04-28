package com.rajesh.microservices.order.service;

import com.rajesh.microservices.order.dto.OrderRequestDTO;
import com.rajesh.microservices.order.dto.OrderResponseDTO;
import com.rajesh.microservices.order.entity.Order;
import com.rajesh.microservices.order.mapper.OrderMapper;
import com.rajesh.microservices.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository repo;

    public OrderService(OrderRepository repo) {
        this.repo = repo;
    }

    // CREATE
    public OrderResponseDTO create(OrderRequestDTO dto, Long userId) {

        // 🔥 Later replace with Product Service call
        Double price = 500.0;

        Order order = OrderMapper.toEntity(dto, userId, price);

        return OrderMapper.toDTO(repo.save(order));
    }

    // GET BY ID
    public OrderResponseDTO getById(Long id) {
        Order order = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return OrderMapper.toDTO(order);
    }

    // GET ALL
    public List<OrderResponseDTO> getAll() {
        return repo.findAll()
                .stream()
                .map(OrderMapper::toDTO)
                .collect(Collectors.toList());
    }

    // UPDATE
    public OrderResponseDTO update(Long id, OrderRequestDTO dto) {

        Order existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        existing.setProductId(dto.getProductId());
        existing.setQuantity(dto.getQuantity());

        return OrderMapper.toDTO(repo.save(existing));
    }

    // DELETE
    public void delete(Long id) {
        Order existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        repo.delete(existing);
    }
}