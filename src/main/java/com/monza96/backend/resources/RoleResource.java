package com.monza96.backend.resources;

import com.monza96.backend.domain.dtos.RoleResponseDTO;
import com.monza96.backend.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/roles")
public class RoleResource {
    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleResponseDTO>> findAll() {
        List<RoleResponseDTO> roles = roleService.findAll();
        return ResponseEntity.ok().body(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponseDTO> findById(@PathVariable Long id) {
        RoleResponseDTO responseDTO = roleService.findById(id);
        return ResponseEntity.ok().body(responseDTO);
    }
}
