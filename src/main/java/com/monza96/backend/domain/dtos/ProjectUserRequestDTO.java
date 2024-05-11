package com.monza96.backend.domain.dtos;

import com.monza96.backend.domain.enums.ProjectAuthority;

public record ProjectUserRequestDTO(
        Long userId,
        ProjectAuthority authority
) {
}
