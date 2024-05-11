package com.monza96.backend.services;

import com.monza96.backend.domain.Project;
import com.monza96.backend.domain.Task;
import com.monza96.backend.domain.dtos.TaskRequestDTO;
import com.monza96.backend.domain.dtos.TaskResponseDTO;
import com.monza96.backend.domain.mappers.TaskMapper;
import com.monza96.backend.repository.TaskRepository;
import com.monza96.backend.services.exceptions.DatabaseException;
import com.monza96.backend.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TaskService {
    private final TaskRepository taskRepository;

    private final ProjectService projectService;

    public List<TaskResponseDTO> findAll(Long projectId) {
        return taskRepository.findByProjectId(projectId).stream().map(x -> TaskMapper.toResponseDTO(x)).toList();
    }

    public TaskResponseDTO findById(Long projectId, Long id) {
        Task task = findEntityByProjectIdAndId(projectId, id);
        return TaskMapper.toResponseDTO(task);
    }

    public TaskResponseDTO create(Long projectId, TaskRequestDTO dto) {
        Project project = projectService.findEntityById(projectId);
        Task task = TaskMapper.toEntity(dto, project);
        return TaskMapper.toResponseDTO(taskRepository.save(task));
    }

    public void delete(Long projectId, Long id) {
        try {
            taskRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(Task.class, id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public TaskResponseDTO update(Long projectId, Long id, TaskRequestDTO dto) {
        Task task = findEntityByProjectIdAndId(projectId, id);

        task.setDescription(dto.description());
        task.setDueTime(dto.dueTime());
        task.setTitle(dto.title());

        return TaskMapper.toResponseDTO(taskRepository.save(task));
    }

    Task findEntityByProjectIdAndId(Long projectId, Long id) {
        return taskRepository.findByProjectIdAndId(projectId, id)
                .orElseThrow(() -> new ResourceNotFoundException(Task.class, id));
    }
}
