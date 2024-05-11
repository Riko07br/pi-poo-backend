package com.monza96.backend.domain.dtos;

public record ClassificationResponseDTO(
        Long id,
        String title,
        Long projectId
) {
}
