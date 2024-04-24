package com.monza96.backend.dtos;

import java.time.LocalDate;

public record ProjectRequestDTO(
        String name,
        String description,
        LocalDate startDate,
        LocalDate endDate
) {
}
