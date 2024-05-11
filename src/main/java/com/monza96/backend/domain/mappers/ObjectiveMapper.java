package com.monza96.backend.domain.mappers;

import com.monza96.backend.domain.Objective;
import com.monza96.backend.domain.Task;
import com.monza96.backend.domain.dtos.ObjectiveRequestDTO;
import com.monza96.backend.domain.dtos.ObjectiveResponseDTO;

public class ObjectiveMapper {
    public static ObjectiveResponseDTO toResponseDTO(Objective task) {
        return new ObjectiveResponseDTO(task.getId(),
                task.getTitle(),
                task.getDescription());
    }

    public static Objective toEntity(ObjectiveRequestDTO dto, Task task) {
        return new Objective(null, dto.title(), dto.description(), task);
    }
}
