package com.monza96.backend.services;

import com.monza96.backend.domain.Project;
import com.monza96.backend.domain.Task;
import com.monza96.backend.domain.dtos.TaskRequestDTO;
import com.monza96.backend.domain.dtos.TaskResponseDTO;
import com.monza96.backend.domain.mappers.TaskMapper;
import com.monza96.backend.repository.TaskRepository;
import com.monza96.backend.resources.QueryParams;
import com.monza96.backend.services.exceptions.DatabaseException;
import com.monza96.backend.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TaskService {
    private final TaskRepository taskRepository;

    private final ProjectService projectService;

    public Page<TaskResponseDTO> findAll(Long projectId) {
        Pageable pageable = PageRequest.of(0, 10);
        return taskRepository.findByProjectId(projectId, pageable).map(x -> TaskMapper.toResponseDTO(x));
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

        task.setTitle(dto.title());
        task.setDescription(dto.description());
        task.setDueTime(dto.dueTime());

        return TaskMapper.toResponseDTO(taskRepository.save(task));
    }

    public Page<TaskResponseDTO> findTasksByUserId(Long userId, QueryParams queryParams) {
        return taskRepository.findByProjectUsersUserId(userId, queryParams.getPageable())
                .map(x -> TaskMapper.toResponseDTO(x));
    }

    Task findEntityByProjectIdAndId(Long projectId, Long id) {
        return taskRepository.findByProjectIdAndId(projectId, id)
                .orElseThrow(() -> new ResourceNotFoundException(Task.class, id));
    }
}
