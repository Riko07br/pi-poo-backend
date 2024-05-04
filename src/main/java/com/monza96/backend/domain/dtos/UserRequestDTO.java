package com.monza96.backend.domain.dtos;

public record UserRequestDTO(
        String email,
        String password
) {
}
