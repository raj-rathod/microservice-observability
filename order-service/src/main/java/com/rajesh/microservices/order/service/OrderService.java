package com.rajesh.microservices.order.service;

import com.rajesh.microservices.order.dto.OrderRequestDTO;
import com.rajesh.microservices.order.dto.OrderResponseDTO;
import com.rajesh.microservices.order.dto.ProductDTO;
import com.rajesh.microservices.order.entity.Order;
import com.rajesh.microservices.order.mapper.OrderMapper;
import com.rajesh.microservices.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository repo;

    @Autowired
    private WebClient webClient;

    public OrderService(OrderRepository repo) {
        this.repo = repo;
    }


    public ProductDTO getProduct(Long productId) {
        return webClient.get()
                .uri("http://localhost:8083/products/" + productId)
                .retrieve()
                .bodyToMono(ProductDTO.class)
                .block(); // blocking for now
    }

    // CREATE
    public OrderResponseDTO create(OrderRequestDTO dto, Long userId) {

        ProductDTO product = getProduct(dto.getProductId());

        Double price = product.getPrice();

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

    // Current log in user user orders
    public List<OrderResponseDTO> getMyOrders() {

        String userId = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        List<Order> orders = repo.findByUserId(Long.valueOf(userId));

        return orders.stream()
                .map(OrderMapper::toDTO)
                .toList();
    }

}