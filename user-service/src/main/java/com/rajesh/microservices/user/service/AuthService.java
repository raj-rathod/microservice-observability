package com.rajesh.microservices.user.service;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.rajesh.microservices.user.component.JwtUtil;
import com.rajesh.microservices.user.dto.LoginRequestDTO;
import com.rajesh.microservices.user.entity.User;
import com.rajesh.microservices.user.repository.UserRepository;

@Service
public class AuthService {

    private final AuthenticationManager authManager;
    private final UserRepository repo;
    private final JwtUtil jwtUtil;

    public AuthService(AuthenticationManager authManager,
                       UserRepository repo,
                       JwtUtil jwtUtil) {
        this.authManager = authManager;
        this.repo = repo;
        this.jwtUtil = jwtUtil;
    }

    public Map<String, String> login(LoginRequestDTO request) {

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = repo.findByEmail(request.getEmail()).get();

        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken();

        user.setRefreshToken(refreshToken);
        user.setRefreshTokenExpiry(LocalDateTime.now().plusDays(7));
        repo.save(user);

        return Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken
        );
    }

    public Map<String, String> refresh(String refreshToken) {

        User user = repo.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (user.getRefreshTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Refresh token expired");
        }

        String newAccessToken = jwtUtil.generateAccessToken(user);

        return Map.of("accessToken", newAccessToken);
    }
}
