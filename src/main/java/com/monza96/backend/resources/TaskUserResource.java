package com.monza96.backend.resources;

import com.monza96.backend.domain.dtos.ProjectUserRequestDTO;
import com.monza96.backend.domain.dtos.ProjectUserResponseDTO;
import com.monza96.backend.services.ProjectUserService;
import com.monza96.backend.services.TaskUserService;
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
@RequestMapping("projects/{projectId}/tasks/{taskId}/users")
public class TaskUserResource {
    private final TaskUserService taskUserService;

    @GetMapping
    public ResponseEntity<Page<ProjectUserResponseDTO>> findAll(@PathVariable Long projectId,
                                                                @PathVariable Long taskId,
                                                                QueryParams queryParams) {
        Page<ProjectUserResponseDTO> projectUsers = taskUserService.findAll(projectId, taskId, queryParams);
        return ResponseEntity.ok().body(projectUsers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectUserResponseDTO> findById(@PathVariable Long projectId,
                                                           @PathVariable Long taskId,
                                                           @PathVariable Long id) {
        ProjectUserResponseDTO responseDTO = taskUserService.findById(projectId, taskId, id);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping("/{id}")
    public ResponseEntity<ProjectUserResponseDTO> create(@PathVariable Long projectId,
                                                         @PathVariable Long taskId,
                                                         @PathVariable Long id) throws URISyntaxException {
        ProjectUserResponseDTO responseDTO = taskUserService.create(projectId, taskId, id);
        return ResponseEntity.created(new URI("projects/" +
                projectId +
                "/tasks/" +
                taskId +
                "/users/"
                + id)).body(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long projectId,
                                       @PathVariable Long taskId,
                                       @PathVariable Long id) {
        taskUserService.delete(projectId, taskId, id);
        return ResponseEntity.noContent().build();
    }

}
