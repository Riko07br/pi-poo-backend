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

    public List<TaskResponseDTO> findAll() {
        return taskRepository.findAll().stream().map(x -> TaskMapper.toResponseDTO(x)).toList();
    }

    public TaskResponseDTO findById(Long id) {
        Task task = findEntityById(id);
        return TaskMapper.toResponseDTO(task);
    }

    public TaskResponseDTO create(TaskRequestDTO dto) {
        Project project = projectService.findEntityById(dto.projectId());
        Task task = TaskMapper.toEntity(dto, project);
        return TaskMapper.toResponseDTO(taskRepository.save(task));
    }

    public void delete(Long id) {
        try {
            taskRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(Task.class, id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public TaskResponseDTO update(Long id, TaskRequestDTO dto) {
        Task task = findEntityById(id);

        task.setTitle(dto.title());
        task.setDescription(dto.description());
        task.setDueTime(dto.dueTime());

        return TaskMapper.toResponseDTO(taskRepository.save(task));
    }

    Task findEntityById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Task.class, id));
    }
}
