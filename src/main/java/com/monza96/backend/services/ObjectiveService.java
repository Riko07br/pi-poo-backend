package com.monza96.backend.services;

import com.monza96.backend.domain.Classification;
import com.monza96.backend.domain.Objective;
import com.monza96.backend.domain.Task;
import com.monza96.backend.domain.dtos.ObjectiveRequestDTO;
import com.monza96.backend.domain.dtos.ObjectiveResponseDTO;
import com.monza96.backend.domain.mappers.ObjectiveMapper;
import com.monza96.backend.repository.ObjectiveRepository;
import com.monza96.backend.services.exceptions.DatabaseException;
import com.monza96.backend.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ObjectiveService {
    private final ObjectiveRepository objectiveRepository;

    private final ClassificationService classificationService;
    private final TaskService taskService;

    public List<ObjectiveResponseDTO> findAll() {
        return objectiveRepository.findAll().stream().map(x -> ObjectiveMapper.toResponseDTO(x)).toList();
    }

    public ObjectiveResponseDTO findById(Long id) {
        Objective objective = findEntityById(id);
        return ObjectiveMapper.toResponseDTO(objective);
    }

    public ObjectiveResponseDTO create(ObjectiveRequestDTO dto) {
        Task task = taskService.findEntityById(dto.taskId());
        Classification classification = classificationService.findEntityById(dto.classificationId());

        Objective objective = ObjectiveMapper.toEntity(dto, task, classification);
        return ObjectiveMapper.toResponseDTO(objectiveRepository.save(objective));
    }

    public void delete(Long id) {
        try {
            objectiveRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(Objective.class, id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public ObjectiveResponseDTO update(Long id, ObjectiveRequestDTO dto) {
        Objective objective = findEntityById(id);

        objective.setTitle(dto.title());
        objective.setDescription(dto.description());

        return ObjectiveMapper.toResponseDTO(objectiveRepository.save(objective));
    }

    Objective findEntityById(Long id) {
        return objectiveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Objective.class, id));
    }
}
