package com.monza96.backend.domain.dtos;

public record ProjectUserResponseDTO(
        Long id,
        UserResponseDTO user,
        RoleResponseDTO role
) {
}
