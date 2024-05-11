package com.monza96.backend.resources;

import com.monza96.backend.domain.dtos.ProjectUserRequestDTO;
import com.monza96.backend.domain.dtos.ProjectUserResponseDTO;
import com.monza96.backend.services.ProjectUserService;
import lombok.RequiredArgsConstructor;
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
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/projects/{projectId}/project-users")
public class ProjectUserResource {
    private final ProjectUserService projectUserService;

    @GetMapping
    public ResponseEntity<List<ProjectUserResponseDTO>> findAll(@PathVariable Long projectId) {
        List<ProjectUserResponseDTO> projectUsers = projectUserService.findAll(projectId);
        return ResponseEntity.ok().body(projectUsers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectUserResponseDTO> findById(@PathVariable Long projectId, @PathVariable Long id) {
        ProjectUserResponseDTO responseDTO = projectUserService.findById(projectId, id);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping
    public ResponseEntity<ProjectUserResponseDTO> create(@PathVariable Long projectId,
                                                         @RequestBody ProjectUserRequestDTO projectUserRequestDTO) throws URISyntaxException {
        ProjectUserResponseDTO responseDTO = projectUserService.create(projectId, projectUserRequestDTO);
        return ResponseEntity.created(new URI("/projects/" +
                projectId +
                "/project-users/" +
                responseDTO.id())).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectUserResponseDTO> update(@PathVariable Long projectId,
                                                         @PathVariable Long id,
                                                         @RequestBody ProjectUserRequestDTO requestDTO) {
        ProjectUserResponseDTO responseDTO = projectUserService.update(projectId, id, requestDTO);
        return ResponseEntity.ok().body(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long projectId,
                                       @PathVariable Long id) {
        projectUserService.delete(projectId, id);
        return ResponseEntity.noContent().build();
    }

}
