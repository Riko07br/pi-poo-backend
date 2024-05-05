package com.monza96.backend.services;

import com.monza96.backend.domain.Project;
import com.monza96.backend.domain.ProjectUser;
import com.monza96.backend.domain.Role;
import com.monza96.backend.domain.User;
import com.monza96.backend.domain.dtos.ProjectUserRequestDTO;
import com.monza96.backend.domain.dtos.ProjectUserResponseDTO;
import com.monza96.backend.domain.mappers.ProjectUserMapper;
import com.monza96.backend.repository.ProjectUserRepository;
import com.monza96.backend.services.exceptions.DatabaseException;
import com.monza96.backend.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectUserService {

    private final ProjectUserRepository projectUserRepository;

    private final ProjectService projectService;
    private final RoleService roleService;
    private final UserService userService;

    public List<ProjectUserResponseDTO> findAll(Long projectId) {
        return projectUserRepository.findAllByProjectId(projectId)
                .stream()
                .map(x -> ProjectUserMapper.toResponseDTO(x)).toList();
    }

    public ProjectUserResponseDTO findById(Long projectId, Long id) {
        ProjectUser projectUser = findEntityByProjectIdAndId(projectId, id);
        return ProjectUserMapper.toResponseDTO(projectUser);
    }

    public ProjectUserResponseDTO create(Long projectId, ProjectUserRequestDTO dto) {
        Project project = projectService.findEntityById(projectId);

        User user = userService.findEntityById(dto.userId());
        project.getProjectUsers().stream()
                .filter(x -> x.getUser().equals(user)).findFirst()
                .ifPresent(x -> {
                    throw new DatabaseException("User already in project, use PUT to update the role");
                });

        Role role = roleService.findEntityById(dto.roleId());

        ProjectUser projectUser = new ProjectUser(null, user, project, role);

        return ProjectUserMapper.toResponseDTO(projectUserRepository.save(projectUser));
    }

    public void delete(Long projectId, Long id) {
        ProjectUser projectUser = findEntityById(id);
        if (!projectUser.getProject().getId().equals(projectId)) {
            throw new ResourceNotFoundException(ProjectUser.class, id);
        }

        try {
            projectUserRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(ProjectUser.class, id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public ProjectUserResponseDTO update(Long projectId, Long id, ProjectUserRequestDTO dto) {
        ProjectUser projectUser = findEntityByProjectIdAndId(projectId, id);

        User userUpdater = userService.findEntityById(dto.userId());
        if (projectUser.getUser().equals(userUpdater)) {
            throw new DatabaseException("An user may not update its own role in a project");
        }

        projectUser.setRole(roleService.findEntityById(dto.roleId()));

        return ProjectUserMapper.toResponseDTO(projectUserRepository.save(projectUser));
    }

    ProjectUser findEntityById(Long id) {
        return projectUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ProjectUser.class, id));
    }

    ProjectUser findEntityByProjectIdAndId(Long id, Long projectId) {
        return projectUserRepository.findByProjectIdAndId(projectId, id)
                .orElseThrow(() -> new ResourceNotFoundException(ProjectUser.class, id));
    }
}
