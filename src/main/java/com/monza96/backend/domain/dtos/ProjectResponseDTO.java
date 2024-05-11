package com.monza96.backend.domain.dtos;

import java.time.LocalDate;
import java.util.Set;

public record ProjectResponseDTO(
        Long id,
        String name,
        String description,
        LocalDate startDate,
        LocalDate endDate
) {
}
