package com.monza96.backend.resources;

import com.monza96.backend.domain.dtos.ProjectRequestDTO;
import com.monza96.backend.domain.dtos.ProjectResponseDTO;
import com.monza96.backend.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
@RequestMapping("/projects")
public class ProjectResource {
    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<ProjectResponseDTO>> findAll() {
        List<ProjectResponseDTO> projects = projectService.findAll();
        return ResponseEntity.ok().body(projects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> findById(@PathVariable Long id) {
        ProjectResponseDTO responseDTO = projectService.findById(id);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping
    public ResponseEntity<ProjectResponseDTO> create(Authentication authentication,
                                                     @RequestBody ProjectRequestDTO projectRequestDTO) throws URISyntaxException {
        ProjectResponseDTO responseDTO = projectService.create(authentication, projectRequestDTO);
        return ResponseEntity.created(new URI("/projects/" + responseDTO.id())).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> update(@PathVariable Long id, @RequestBody ProjectRequestDTO requestDTO) {
        ProjectResponseDTO responseDTO = projectService.update(id, requestDTO);
        return ResponseEntity.ok().body(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
