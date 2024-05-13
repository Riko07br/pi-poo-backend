package com.monza96.backend.services;

import com.monza96.backend.domain.Classification;
import com.monza96.backend.domain.Objective;
import com.monza96.backend.domain.Task;
import com.monza96.backend.domain.dtos.ObjectiveRequestDTO;
import com.monza96.backend.domain.dtos.ObjectiveResponseDTO;
import com.monza96.backend.domain.mappers.ObjectiveMapper;
import com.monza96.backend.repository.ObjectiveRepository;
import com.monza96.backend.resources.QueryParams;
import com.monza96.backend.services.exceptions.DatabaseException;
import com.monza96.backend.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ObjectiveService {
    private final ObjectiveRepository objectiveRepository;

    private final ClassificationService classificationService;
    private final TaskService taskService;

    public Page<ObjectiveResponseDTO> findAll(Long projectId, Long taskId, QueryParams queryParams) {
        return objectiveRepository
                .findByTaskProjectIdAndTaskId(projectId, taskId, queryParams.getPageable())
                .map(x -> ObjectiveMapper.toResponseDTO(x));
    }

    public ObjectiveResponseDTO findById(Long projectId, Long taskId, Long id) {
        Objective objective = findEntityByProjectIdAndTaskIdAndId(projectId, taskId, id);
        return ObjectiveMapper.toResponseDTO(objective);
    }

    public ObjectiveResponseDTO create(Long projectId, Long taskId, ObjectiveRequestDTO dto) {
        Task task = taskService.findEntityByProjectIdAndId(projectId, taskId);
        Classification classification = classificationService.findEntityById(dto.classificationId());

        Objective objective = ObjectiveMapper.toEntity(dto, task, classification);
        return ObjectiveMapper.toResponseDTO(objectiveRepository.save(objective));
    }

    public void delete(Long projectId, Long taskId, Long id) {
        try {
            objectiveRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(Objective.class, id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public ObjectiveResponseDTO update(Long projectId, Long taskId, Long id, ObjectiveRequestDTO dto) {
        Objective objective = findEntityByProjectIdAndTaskIdAndId(projectId, taskId, id);

        objective.setTitle(dto.title());
        objective.setDescription(dto.description());

        return ObjectiveMapper.toResponseDTO(objectiveRepository.save(objective));
    }

    Objective findEntityById(Long id) {
        return objectiveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Objective.class, id));
    }

    Objective findEntityByProjectIdAndTaskIdAndId(Long projectId, Long taskId, Long id) {
        return objectiveRepository.findByTaskProjectIdAndTaskIdAndId(projectId, taskId, id)
                .orElseThrow(() -> new ResourceNotFoundException(Objective.class, id));
    }
}
