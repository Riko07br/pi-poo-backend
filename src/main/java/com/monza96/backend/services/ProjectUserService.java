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

    public List<ProjectUserResponseDTO> findAll() {
        return projectUserRepository.findAll().stream().map(x -> ProjectUserMapper.toResponseDTO(x)).toList();
    }

    public ProjectUserResponseDTO findById(Long id) {
        ProjectUser projectUser = findEntityById(id);
        return ProjectUserMapper.toResponseDTO(projectUser);
    }

    public ProjectUserResponseDTO create(ProjectUserRequestDTO dto) {
        //every user should send all the data
        User user = userService.findEntityById(dto.userId());
        Role role = roleService.findEntityById(dto.roleId());
        Project project = projectService.findEntityById(dto.projectId());

        ProjectUser projectUser = new ProjectUser(null, user, project, role);
        return ProjectUserMapper.toResponseDTO(projectUserRepository.save(projectUser));
    }

    public void delete(Long id) {
        try {
            projectUserRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(ProjectUser.class, id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public ProjectUserResponseDTO update(Long id, ProjectUserRequestDTO dto) {
        ProjectUser projectUser = findEntityById(id);

        //TODO add more logic here, an user may not self update the role
        //can only update the role
        projectUser.setRole(roleService.findEntityById(dto.roleId()));

        return ProjectUserMapper.toResponseDTO(projectUserRepository.save(projectUser));
    }

    ProjectUser findEntityById(Long id) {
        return projectUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ProjectUser.class, id));
    }
}
