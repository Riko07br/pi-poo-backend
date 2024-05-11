package com.monza96.backend.resources;

import com.monza96.backend.domain.dtos.ClassificationRequestDTO;
import com.monza96.backend.domain.dtos.ClassificationResponseDTO;
import com.monza96.backend.services.ClassificationService;
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
@RequestMapping("/classifications")
public class ClassificationResource {
    private final ClassificationService classificationService;

    @GetMapping
    public ResponseEntity<List<ClassificationResponseDTO>> findAll() {
        List<ClassificationResponseDTO> classifications = classificationService.findAll();
        return ResponseEntity.ok().body(classifications);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassificationResponseDTO> findById(@PathVariable Long id) {
        ClassificationResponseDTO responseDTO = classificationService.findById(id);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping
    public ResponseEntity<ClassificationResponseDTO> create(@RequestBody ClassificationRequestDTO classificationRequestDTO) throws URISyntaxException {
        ClassificationResponseDTO responseDTO = classificationService.create(classificationRequestDTO);
        return ResponseEntity.created(new URI("/classifications/" + responseDTO.id())).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassificationResponseDTO> update(@PathVariable Long id,
                                                            @RequestBody ClassificationRequestDTO requestDTO) {
        ClassificationResponseDTO responseDTO = classificationService.update(id, requestDTO);
        return ResponseEntity.ok().body(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        classificationService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
