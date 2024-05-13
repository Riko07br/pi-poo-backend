package com.monza96.backend.resources;

import com.monza96.backend.domain.dtos.ClassificationRequestDTO;
import com.monza96.backend.domain.dtos.ClassificationResponseDTO;
import com.monza96.backend.services.ClassificationService;
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
@RequestMapping("/projects/{projectId}/classifications")
public class ClassificationResource {
    private final ClassificationService classificationService;

    @GetMapping
    public ResponseEntity<Page<ClassificationResponseDTO>> findAll(@PathVariable Long projectId, QueryParams queryParams) {
        Page<ClassificationResponseDTO> classifications = classificationService.findAll(projectId, queryParams);
        return ResponseEntity.ok().body(classifications);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassificationResponseDTO> findById(@PathVariable Long projectId, @PathVariable Long id) {
        ClassificationResponseDTO responseDTO = classificationService.findById(projectId, id);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping
    public ResponseEntity<ClassificationResponseDTO> create(
            @PathVariable Long projectId,
            @RequestBody ClassificationRequestDTO classificationRequestDTO) throws URISyntaxException {

        ClassificationResponseDTO responseDTO = classificationService.create(projectId, classificationRequestDTO);
        return ResponseEntity.created(new URI("/projects/" + projectId +
                "/classifications/" + responseDTO.id())).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassificationResponseDTO> update(@PathVariable Long projectId,
                                                            @PathVariable Long id,
                                                            @RequestBody ClassificationRequestDTO requestDTO) {
        ClassificationResponseDTO responseDTO = classificationService.update(projectId, id, requestDTO);
        return ResponseEntity.ok().body(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long projectId, @PathVariable Long id) {
        classificationService.delete(projectId, id);
        return ResponseEntity.noContent().build();
    }

}
