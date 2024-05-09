package com.monza96.backend.domain.dtos;

import com.monza96.backend.domain.enums.ProjectAuthority;

public record ProjectUserResponseDTO(
        Long id,
        UserResponseDTO user,
        ProjectAuthority authority
) {
}
