package com.monza96.backend.dtos;

public record ProjectUserResponseDTO(
        Long id,
        UserResponseDTO user,
        RoleResponseDTO role
) {
}
