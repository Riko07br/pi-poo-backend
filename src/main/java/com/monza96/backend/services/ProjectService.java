package com.monza96.backend.services;

import com.monza96.backend.domain.Project;
import com.monza96.backend.domain.ProjectUser;
import com.monza96.backend.domain.Role;
import com.monza96.backend.domain.User;
import com.monza96.backend.domain.dtos.ProjectRequestDTO;
import com.monza96.backend.domain.dtos.ProjectResponseDTO;
import com.monza96.backend.domain.enums.ProjectAuthority;
import com.monza96.backend.domain.mappers.ProjectMapper;
import com.monza96.backend.repository.ProjectRepository;
import com.monza96.backend.repository.ProjectUserRepository;
import com.monza96.backend.services.exceptions.DatabaseException;
import com.monza96.backend.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    private final ProjectUserRepository projectUserRepository;  //service is not used to avoid circular dependency
    private final RoleService roleService;
    private final UserService userService;

    public Page<ProjectResponseDTO> findAll(Authentication authentication) {
        String userEmail = authentication.getName();
        Pageable pageable = PageRequest.of(0, 10);
        return projectRepository
                .findByProjectUsersUserEmail(userEmail, pageable)
                .map(x -> ProjectMapper.toResponseDTO(x));
    }

    public ProjectResponseDTO findById(Long id) {
        Project project = findEntityById(id);
        return ProjectMapper.toResponseDTO(project);
    }

    public ProjectResponseDTO create(Authentication authentication, ProjectRequestDTO dto) {

        User projectCreator = userService.findEntityByEmail(authentication.getName());
        Role creatorRole = roleService.findEntityByAuthority(ProjectAuthority.CREATOR);

        Project project = new Project(null, dto.name(), dto.description(), dto.startDate(), dto.endDate());
        project = projectRepository.save(project);

        projectUserRepository.save(new ProjectUser(null, projectCreator, project, creatorRole));

        return ProjectMapper.toResponseDTO(project);
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
        Project project = findEntityById(id);

        project.setName(dto.name());
        project.setDescription(dto.description());
        project.setStartDate(dto.startDate());
        project.setEndDate(dto.endDate());

        return ProjectMapper.toResponseDTO(projectRepository.save(project));
    }

    Project findEntityById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Project.class, id));
    }
}
