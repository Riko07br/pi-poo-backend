package com.monza96.backend.services;

import com.monza96.backend.domain.Project;
import com.monza96.backend.dtos.ProjectRequestDTO;
import com.monza96.backend.dtos.ProjectResponseDTO;
import com.monza96.backend.mappers.ProjectMapper;
import com.monza96.backend.repository.ProjectRepository;
import com.monza96.backend.services.exceptions.DatabaseException;
import com.monza96.backend.services.exceptions.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository userRepository;

    public ProjectService(ProjectRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<ProjectResponseDTO> findAll() {
        return userRepository.findAll().stream().map(x -> ProjectMapper.toResponseDTO(x)).toList();
    }

    public ProjectResponseDTO findById(Long id) {
        Project user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Project.class, id));

        return ProjectMapper.toResponseDTO(user);
    }

    public ProjectResponseDTO create(ProjectRequestDTO dto) {
        Project user = ProjectMapper.toEntity(dto);
        return ProjectMapper.toResponseDTO(userRepository.save(user));
    }

    public void delete(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(Project.class, id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public ProjectResponseDTO update(Long id, ProjectRequestDTO dto) {
        Project user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Project.class, id));

        user.setEmail(dto.email());
        user.setPassword(dto.password());

        return ProjectMapper.toResponseDTO(userRepository.save(user));
    }
}
