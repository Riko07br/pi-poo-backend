package com.monza96.backend.services;

import com.monza96.backend.domain.ProjectUser;
import com.monza96.backend.domain.Task;
import com.monza96.backend.domain.dtos.ProjectUserResponseDTO;
import com.monza96.backend.domain.mappers.ProjectUserMapper;
import com.monza96.backend.repository.ProjectUserRepository;
import com.monza96.backend.resources.QueryParams;
import com.monza96.backend.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskUserService {
    private final ProjectUserRepository projectUserRepository;

    private final TaskService taskService;

    public Page<ProjectUserResponseDTO> findAll(Long projectId, Long taskId, QueryParams queryParams) {
        return projectUserRepository
                .findByProjectIdAndTasksId(projectId, taskId, queryParams.getPageable())
                .map(x -> ProjectUserMapper.toResponseDTO(x));
    }

    public ProjectUserResponseDTO findById(Long projectId, Long taskId, Long id) {
        ProjectUser projectUser = findEntityByProjectIdAndTaskIdAndId(projectId, taskId, id);
        return ProjectUserMapper.toResponseDTO(projectUser);
    }

    public ProjectUserResponseDTO create(Long projectId, Long taskId, Long id) {
        Task task = taskService.findEntityByProjectIdAndId(projectId, taskId);
        ProjectUser projectUser = projectUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ProjectUser.class, id));
        projectUser.getTasks().add(task);

        return ProjectUserMapper.toResponseDTO(projectUserRepository.save(projectUser));
    }

    public void delete(Long projectId, Long taskId, Long id) {
        Task task = taskService.findEntityByProjectIdAndId(projectId, taskId);
        ProjectUser projectUser = projectUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ProjectUser.class, id));
        projectUser.getTasks().remove(task);
        projectUserRepository.save(projectUser);
    }

    private ProjectUser findEntityByProjectIdAndTaskIdAndId(Long projectId, Long taskId, Long id) {
        return projectUserRepository.findByProjectIdAndTasksIdAndId(projectId, taskId, id)
                .orElseThrow(() -> new ResourceNotFoundException(ProjectUser.class, id));
    }
}
