package com.monza96.backend.resources;

import com.monza96.backend.domain.dtos.ObjectiveRequestDTO;
import com.monza96.backend.domain.dtos.ObjectiveResponseDTO;
import com.monza96.backend.services.ObjectiveService;
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
@RequestMapping("/projects/{projectId}/tasks/{taskId}/objectives")
public class ObjectiveResource {
    private final ObjectiveService objectiveService;

    @GetMapping
    public ResponseEntity<Page<ObjectiveResponseDTO>> findAll(@PathVariable Long projectId,
                                                              @PathVariable Long taskId,
                                                              QueryParams queryParams) {
        Page<ObjectiveResponseDTO> objectives = objectiveService.findAll(projectId, taskId, queryParams);
        return ResponseEntity.ok().body(objectives);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObjectiveResponseDTO> findById(@PathVariable Long projectId,
                                                         @PathVariable Long taskId,
                                                         @PathVariable Long id) {
        ObjectiveResponseDTO responseDTO = objectiveService.findById(projectId, taskId, id);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping
    public ResponseEntity<ObjectiveResponseDTO> create(@PathVariable Long projectId,
                                                       @PathVariable Long taskId,
                                                       @RequestBody ObjectiveRequestDTO objectiveRequestDTO) throws URISyntaxException {
        ObjectiveResponseDTO responseDTO = objectiveService.create(projectId, taskId, objectiveRequestDTO);
        return ResponseEntity.created(new URI("/projects/" + projectId +
                "/tasks/" + taskId +
                "/objectives/" + responseDTO.id())).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ObjectiveResponseDTO> update(@PathVariable Long projectId,
                                                       @PathVariable Long taskId,
                                                       @PathVariable Long id,
                                                       @RequestBody ObjectiveRequestDTO requestDTO) {
        ObjectiveResponseDTO responseDTO = objectiveService.update(projectId, taskId, id, requestDTO);
        return ResponseEntity.ok().body(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long projectId,
                                       @PathVariable Long taskId,
                                       @PathVariable Long id) {
        objectiveService.delete(projectId, taskId, id);
        return ResponseEntity.noContent().build();
    }

}
