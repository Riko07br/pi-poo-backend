package com.monza96.backend.domain.dtos;

public record ObjectiveRequestDTO(
        String title,
        String description,
        Long taskId,
        Long classificationId
) {
}
