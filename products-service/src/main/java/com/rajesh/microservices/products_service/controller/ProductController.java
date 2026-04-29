package com.rajesh.microservices.products_service.controller;

import com.rajesh.microservices.products_service.dto.ProductRequestDTO;
import com.rajesh.microservices.products_service.dto.ProductResponseDTO;
import com.rajesh.microservices.products_service.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service){
        this.service = service;
    }


    @PostMapping
    public ProductResponseDTO create(@RequestBody ProductRequestDTO dto){
        return  service.create(dto);
    }


    // GET ALL
    @GetMapping
    public List<ProductResponseDTO> getAll() {
        return service.getAll();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ProductResponseDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ProductResponseDTO update(
            @PathVariable Long id,
            @RequestBody ProductRequestDTO dto) {

        return service.update(id, dto);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Product deleted successfully";
    }


}
