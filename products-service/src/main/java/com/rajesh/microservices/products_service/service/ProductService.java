package com.rajesh.microservices.products_service.service;

import com.rajesh.microservices.products_service.dto.ProductRequestDTO;
import com.rajesh.microservices.products_service.dto.ProductResponseDTO;
import com.rajesh.microservices.products_service.entity.Product;
import com.rajesh.microservices.products_service.mapper.ProductMapper;
import com.rajesh.microservices.products_service.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository repo;

    public ProductService(ProductRepository repo){
        this.repo = repo;
    }

    public ProductResponseDTO create(ProductRequestDTO dto){
        Product product = ProductMapper.toEntity(dto);
        return ProductMapper.toDto(repo.save(product));
    }

    public ProductResponseDTO getById(Long id){
        Product product = repo.findById(id).orElseThrow(
                ()-> new RuntimeException("Product not found")
        );

        return ProductMapper.toDto(product);
    }

    public List<ProductResponseDTO> getAll(){
      return repo.findAll()
              .stream()
              .map(ProductMapper::toDto)
              .collect(Collectors.toList());
    }

    public ProductResponseDTO update(Long id, ProductRequestDTO dto){
        Product existing =  repo.findById(id).orElseThrow(
                ()-> new RuntimeException("Product not found")
        );

        existing.setName(dto.getName());
        existing.setPrice(dto.getPrice());
        existing.setQuantity(dto.getQuantity());

        return ProductMapper.toDto(repo.save(existing));
    }

    public void delete(Long id) {
        Product existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        repo.delete(existing);
    }

}
