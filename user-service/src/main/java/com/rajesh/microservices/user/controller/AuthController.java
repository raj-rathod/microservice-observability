package com.rajesh.microservices.user.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rajesh.microservices.user.dto.LoginRequestDTO;
import com.rajesh.microservices.user.dto.UserRequestDTO;
import com.rajesh.microservices.user.dto.UserResponseDTO;
import com.rajesh.microservices.user.service.AuthService;
import com.rajesh.microservices.user.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;
    private final UserService userService;

    public AuthController(AuthService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequestDTO request) {
        return service.login(request);
    }

      // CREATE
    @PostMapping("/registration")
    public UserResponseDTO create(@Valid @RequestBody UserRequestDTO dto) {
        return userService.create(dto);
    }

    @PostMapping("/refresh")
    public Map<String, String> refresh(@RequestParam String refreshToken) {
        return service.refresh(refreshToken);
    }
}
