package com.monza96.backend.services;

import com.monza96.backend.domain.Role;
import com.monza96.backend.dtos.RoleResponseDTO;
import com.monza96.backend.mappers.RoleMapper;
import com.monza96.backend.repository.RoleRepository;
import com.monza96.backend.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository RoleRepository) {
        this.roleRepository = RoleRepository;
    }

    public List<RoleResponseDTO> findAll() {
        return roleRepository.findAll().stream().map(x -> RoleMapper.toResponseDTO(x)).toList();
    }

    public RoleResponseDTO findById(Long id) {
        Role Role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Role.class, id));

        return RoleMapper.toResponseDTO(Role);
    }
}
