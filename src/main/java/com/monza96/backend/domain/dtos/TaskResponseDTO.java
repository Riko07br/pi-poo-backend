package com.monza96.backend.domain.dtos;

import java.time.Instant;
import java.util.Set;

public record TaskResponseDTO(
        Long id,
        String title,
        String description,
        Instant dueTime,
        ProjectResponseDTO project,
        Set<ProjectUserResponseDTO> projectUsers
) {
}
