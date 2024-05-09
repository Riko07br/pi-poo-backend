package com.monza96.backend.domain.mappers;

import com.monza96.backend.domain.ProjectUser;
import com.monza96.backend.domain.dtos.ProjectUserResponseDTO;

public class ProjectUserMapper {

    //TODO maybe add flags to omit some attributes (like project and avoid circular dependencies)
    public static ProjectUserResponseDTO toResponseDTO(ProjectUser entity) {
        return new ProjectUserResponseDTO(
                entity.getId(),
                UserMapper.toResponseDTO(entity.getUser()),
                entity.getRole().getAuthority()
        );
    }
}
