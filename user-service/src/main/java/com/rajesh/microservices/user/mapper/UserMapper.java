package com.rajesh.microservices.user.mapper;

import com.rajesh.microservices.user.entity.User;
import com.rajesh.microservices.user.dto.*;

public class UserMapper {

    public static User toEntity(UserRequestDTO dto) {
        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .build();
    }

    public static UserResponseDTO toDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}
