package com.monza96.backend.domain.mappers;

import com.monza96.backend.domain.Project;
import com.monza96.backend.domain.dtos.ProjectResponseDTO;

import java.util.stream.Collectors;

public class ProjectMapper {
    public static ProjectResponseDTO toResponseDTO(Project entity) {
        return new ProjectResponseDTO(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getProjectUsers().stream().map(x -> ProjectUserMapper.toResponseDTO(x)).collect(Collectors.toSet())
        );
    }
}
