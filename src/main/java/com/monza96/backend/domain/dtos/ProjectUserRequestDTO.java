package com.monza96.backend.domain.dtos;

public record ProjectUserRequestDTO(
        Long userId,
        Long projectId,
        Long roleId
) {
}
