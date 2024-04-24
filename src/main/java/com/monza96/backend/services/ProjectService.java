package com.monza96.backend.services;

import com.monza96.backend.domain.Project;
import com.monza96.backend.dtos.ProjectRequestDTO;
import com.monza96.backend.dtos.ProjectResponseDTO;
import com.monza96.backend.mappers.ProjectMapper;
import com.monza96.backend.repository.ProjectRepository;
import com.monza96.backend.services.exceptions.DatabaseException;
import com.monza96.backend.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;


    public List<ProjectResponseDTO> findAll() {
        return projectRepository.findAll().stream().map(x -> ProjectMapper.toResponseDTO(x)).toList();
    }

    public ProjectResponseDTO findById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Project.class, id));

        return ProjectMapper.toResponseDTO(project);
    }

    public ProjectResponseDTO create(ProjectRequestDTO dto) {
        Project project = new Project(null, dto.name(), dto.description(), dto.startDate(), dto.endDate());

        //find the creator user

        //save the project

        //save the creator ProjectUser

        //TODO other users are added through the projectUser service

        return ProjectMapper.toResponseDTO(projectRepository.save(project));
    }

    public void delete(Long id) {
        try {
            projectRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(Project.class, id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public ProjectResponseDTO update(Long id, ProjectRequestDTO dto) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Project.class, id));

        project.setName(dto.name());
        project.setDescription(dto.description());
        project.setStartDate(dto.startDate());
        project.setEndDate(dto.endDate());

        //TODO update projectUser on the corresponding service

        return ProjectMapper.toResponseDTO(projectRepository.save(project));
    }
}
