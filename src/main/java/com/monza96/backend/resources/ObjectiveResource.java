package com.monza96.backend.resources;

import com.monza96.backend.domain.dtos.ObjectiveRequestDTO;
import com.monza96.backend.domain.dtos.ObjectiveResponseDTO;
import com.monza96.backend.services.ObjectiveService;
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
@RequestMapping("/objectives")
public class ObjectiveResource {
    private final ObjectiveService objectiveService;

    @GetMapping
    public ResponseEntity<List<ObjectiveResponseDTO>> findAll() {
        List<ObjectiveResponseDTO> objectives = objectiveService.findAll();
        return ResponseEntity.ok().body(objectives);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObjectiveResponseDTO> findById(@PathVariable Long id) {
        ObjectiveResponseDTO responseDTO = objectiveService.findById(id);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping
    public ResponseEntity<ObjectiveResponseDTO> create(@RequestBody ObjectiveRequestDTO objectiveRequestDTO) throws URISyntaxException {
        ObjectiveResponseDTO responseDTO = objectiveService.create(objectiveRequestDTO);
        return ResponseEntity.created(new URI("/objective/" + responseDTO.id())).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ObjectiveResponseDTO> update(@PathVariable Long id,
                                                       @RequestBody ObjectiveRequestDTO requestDTO) {
        ObjectiveResponseDTO responseDTO = objectiveService.update(id, requestDTO);
        return ResponseEntity.ok().body(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        objectiveService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
