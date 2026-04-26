package com.rajesh.microservices.user.dto;

import lombok.*;

@Getter
@AllArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
}
