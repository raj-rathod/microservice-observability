package com.rajesh.microservices.user.controller;

import com.rajesh.microservices.user.dto.*;
import com.rajesh.microservices.user.service.UserService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }


    // GET BY ID
    @GetMapping("/{id}")
    public UserResponseDTO get(@PathVariable Long id) {
        return service.get(id);
    }

    // GET ALL
    @GetMapping
    public List<UserResponseDTO> getAll() {
        return service.getAll();
    }

    // UPDATE
    @PutMapping("/{id}")
    public UserResponseDTO update(@PathVariable Long id,
                                  @Valid @RequestBody UserRequestDTO dto) {
        return service.update(id, dto);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
