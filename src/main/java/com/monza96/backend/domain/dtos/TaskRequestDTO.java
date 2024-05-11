package com.monza96.backend.domain.dtos;

import java.time.Instant;

public record TaskRequestDTO(
        String title,
        String description,
        Instant dueTime,
        Long projectId
) {
}
