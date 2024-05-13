package com.monza96.backend.domain.mappers;

import com.monza96.backend.domain.Project;
import com.monza96.backend.domain.Task;
import com.monza96.backend.domain.dtos.TaskRequestDTO;
import com.monza96.backend.domain.dtos.TaskResponseDTO;

import java.util.stream.Collectors;

public class TaskMapper {
    public static TaskResponseDTO toResponseDTO(Task task) {
        return new TaskResponseDTO(task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueTime(),
                ProjectMapper.toResponseDTO(task.getProject()),
                task.getProjectUsers()
                        .stream()
                        .map(projectUser -> ProjectUserMapper.toResponseDTO(projectUser))
                        .collect(Collectors.toSet()));
    }

    public static Task toEntity(TaskRequestDTO dto, Project project) {
        return new Task(null, dto.title(), dto.description(), dto.dueTime(), project);
    }
}
