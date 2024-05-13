package com.monza96.backend.services;

import com.monza96.backend.domain.Classification;
import com.monza96.backend.domain.Project;
import com.monza96.backend.domain.dtos.ClassificationRequestDTO;
import com.monza96.backend.domain.dtos.ClassificationResponseDTO;
import com.monza96.backend.domain.mappers.ClassificationMapper;
import com.monza96.backend.repository.ClassificationRepository;
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
public class ClassificationService {
    private final ClassificationRepository classificationRepository;

    private final ProjectService projectService;

    public Page<ClassificationResponseDTO> findAll(Long projectId, QueryParams queryParams) {
        return classificationRepository.findByProjectId(projectId, queryParams.getPageable())
                .map(x -> ClassificationMapper.toResponseDTO(x));
    }

    public ClassificationResponseDTO findById(Long projectId, Long id) {
        Classification classification = findEntityByProjectIdAndId(projectId, id);
        return ClassificationMapper.toResponseDTO(classification);
    }

    public ClassificationResponseDTO create(Long projectId, ClassificationRequestDTO dto) {
        Project project = projectService.findEntityById(projectId);
        Classification classification = ClassificationMapper.toEntity(dto, project);
        return ClassificationMapper.toResponseDTO(classificationRepository.save(classification));
    }

    public void delete(Long projectId, Long id) {
        try {
            classificationRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(Classification.class, id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public ClassificationResponseDTO update(Long projectId, Long id, ClassificationRequestDTO dto) {
        Classification classification = findEntityByProjectIdAndId(projectId, id);

        classification.setTitle(dto.title());

        return ClassificationMapper.toResponseDTO(classificationRepository.save(classification));
    }

    Classification findEntityById(Long id) {
        return classificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Classification.class, id));
    }

    Classification findEntityByProjectIdAndId(Long projectId, Long id) {
        return classificationRepository.findByProjectIdAndId(projectId, id)
                .orElseThrow(() -> new ResourceNotFoundException(Classification.class, id));
    }
}
