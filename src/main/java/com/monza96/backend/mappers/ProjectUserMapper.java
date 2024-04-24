package com.monza96.backend.mappers;

import com.monza96.backend.domain.ProjectUser;
import com.monza96.backend.dtos.ProjectUserResponseDTO;

public class ProjectUserMapper {

    //TODO maybe add flags to omit some attributes (like project and avoid circular dependencies)
    public static ProjectUserResponseDTO toResponseDTO(ProjectUser entity) {
        return new ProjectUserResponseDTO(
                entity.getId(),
                UserMapper.toResponseDTO(entity.getUser()),
                RoleMapper.toResponseDTO(entity.getRole())
        );
    }
}