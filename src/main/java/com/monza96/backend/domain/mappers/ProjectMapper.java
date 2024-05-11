package com.monza96.backend.domain.mappers;

import com.monza96.backend.domain.Project;
import com.monza96.backend.domain.dtos.ProjectResponseDTO;

public class ProjectMapper {
    public static ProjectResponseDTO toResponseDTO(Project entity) {
        return new ProjectResponseDTO(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getStartDate(),
                entity.getEndDate()
        );
    }
}
