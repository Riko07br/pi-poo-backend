package com.monza96.backend.domain.mappers;

import com.monza96.backend.domain.Classification;
import com.monza96.backend.domain.Project;
import com.monza96.backend.domain.dtos.ClassificationRequestDTO;
import com.monza96.backend.domain.dtos.ClassificationResponseDTO;

public class ClassificationMapper {
    public static ClassificationResponseDTO toResponseDTO(Classification task) {
        return new ClassificationResponseDTO(task.getId(),
                task.getTitle());
    }

    public static Classification toEntity(ClassificationRequestDTO dto, Project project) {
        return new Classification(null, dto.title(), project);
    }
}
