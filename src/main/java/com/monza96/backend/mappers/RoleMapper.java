package com.monza96.backend.mappers;

import com.monza96.backend.domain.Role;
import com.monza96.backend.dtos.RoleResponseDTO;

public class RoleMapper {
    public static RoleResponseDTO toResponseDTO(Role role) {
        return new RoleResponseDTO(role.getId(), role.getTitle(), role.getDescription());
    }
}
