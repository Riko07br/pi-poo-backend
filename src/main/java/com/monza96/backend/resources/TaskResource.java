package com.monza96.backend.resources;

import com.monza96.backend.domain.dtos.TaskRequestDTO;
import com.monza96.backend.domain.dtos.TaskResponseDTO;
import com.monza96.backend.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/projects/{projectId}/tasks")
public class TaskResource {
    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<Page<TaskResponseDTO>> findAll(@PathVariable Long projectId) {
        return ResponseEntity.ok().body(taskService.findAll(projectId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> findById(@PathVariable Long projectId, @PathVariable Long id) {
        TaskResponseDTO responseDTO = taskService.findById(projectId, id);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> create(@PathVariable Long projectId,
                                                  @RequestBody TaskRequestDTO taskRequestDTO) throws URISyntaxException {
        TaskResponseDTO responseDTO = taskService.create(projectId, taskRequestDTO);
        return ResponseEntity.created(new URI("/projects/" +
                projectId +
                "/tasks/" +
                responseDTO.id())).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> update(@PathVariable Long projectId,
                                                  @PathVariable Long id,
                                                  @RequestBody TaskRequestDTO requestDTO) {
        TaskResponseDTO responseDTO = taskService.update(projectId, id, requestDTO);
        return ResponseEntity.ok().body(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long projectId, @PathVariable Long id) {
        taskService.delete(projectId, id);
        return ResponseEntity.noContent().build();
    }
    // TODO nested routes can be added on the corresponding resource
    // TODO so users may have the projects nested route
    // TODO this may avoid top leve deep nested routes only for finding methods
}
