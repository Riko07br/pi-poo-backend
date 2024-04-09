package com.monza96.backend.dtos;

public record ProjectResponseDTO(
        Long id,
        String name,
        String description,
        String status,
        String startDate,
        String endDate,
        String createdAt,
        String updatedAt
) {
}
