package com.monza96.backend.services;

import com.monza96.backend.domain.Project;
import com.monza96.backend.domain.Classification;
import com.monza96.backend.domain.dtos.ClassificationRequestDTO;
import com.monza96.backend.domain.dtos.ClassificationResponseDTO;
import com.monza96.backend.domain.mappers.ClassificationMapper;
import com.monza96.backend.repository.ClassificationRepository;
import com.monza96.backend.services.exceptions.DatabaseException;
import com.monza96.backend.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ClassificationService {
    private final ClassificationRepository classificationRepository;

    private final ProjectService projectService;

    public List<ClassificationResponseDTO> findAll() {
        return classificationRepository.findAll().stream().map(x -> ClassificationMapper.toResponseDTO(x)).toList();
    }

    public ClassificationResponseDTO findById(Long id) {
        Classification classification = findEntityById(id);
        return ClassificationMapper.toResponseDTO(classification);
    }

    public ClassificationResponseDTO create(ClassificationRequestDTO dto) {
        Project project = projectService.findEntityById(dto.projectId());
        Classification classification = ClassificationMapper.toEntity(dto, project);
        return ClassificationMapper.toResponseDTO(classificationRepository.save(classification));
    }

    public void delete(Long id) {
        try {
            classificationRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(Classification.class, id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public ClassificationResponseDTO update(Long id, ClassificationRequestDTO dto) {
        Classification classification = findEntityById(id);

        classification.setTitle(dto.title());

        return ClassificationMapper.toResponseDTO(classificationRepository.save(classification));
    }

    Classification findEntityById(Long id) {
        return classificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Classification.class, id));
    }
}
