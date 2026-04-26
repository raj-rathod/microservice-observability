package com.rajesh.microservices.user.service;

import com.rajesh.microservices.user.entity.User;
import com.rajesh.microservices.user.repository.UserRepository;
import com.rajesh.microservices.user.dto.*;
import com.rajesh.microservices.user.mapper.UserMapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    // CREATE
    public UserResponseDTO create(UserRequestDTO dto) {
        if (repo.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = UserMapper.toEntity(dto);

        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        User savedUser = repo.save(user);

        return UserMapper.toDTO(savedUser);
    }

    // GET BY ID
    public UserResponseDTO get(Long id) {
        User user = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserMapper.toDTO(user);
    }

    // GET ALL
    public List<UserResponseDTO> getAll() {
        return repo.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    // UPDATE
    public UserResponseDTO update(Long id, UserRequestDTO dto) {
        User user = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        return UserMapper.toDTO(repo.save(user));
    }

    // DELETE
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        repo.deleteById(id);
    }
}
