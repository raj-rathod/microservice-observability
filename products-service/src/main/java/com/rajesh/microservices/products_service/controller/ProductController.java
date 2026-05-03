package com.rajesh.microservices.products_service.controller;

import com.rajesh.microservices.products_service.dto.ProductRequestDTO;
import com.rajesh.microservices.products_service.dto.ProductResponseDTO;
import com.rajesh.microservices.products_service.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
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
    public List<ProductResponseDTO> getAll(@RequestHeader Map<String, String> headers) {
        log.info("Entering Product Controller - getAll()");
        headers.forEach((k, v) -> System.out.println(k + " = " + v));
        return service.getAll();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ProductResponseDTO getById(@PathVariable Long id) {
        log.info("Entering Product Controller - getById()");
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
