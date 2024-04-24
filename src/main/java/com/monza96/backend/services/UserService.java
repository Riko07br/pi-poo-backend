package com.monza96.backend.services;

import com.monza96.backend.domain.User;
import com.monza96.backend.dtos.UserRequestDTO;
import com.monza96.backend.dtos.UserResponseDTO;
import com.monza96.backend.mappers.UserMapper;
import com.monza96.backend.repository.UserRepository;
import com.monza96.backend.services.exceptions.DatabaseException;
import com.monza96.backend.services.exceptions.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponseDTO> findAll() {
        return userRepository.findAll().stream().map(x -> UserMapper.toResponseDTO(x)).toList();
    }

    public UserResponseDTO findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(User.class, id));

        return UserMapper.toResponseDTO(user);
    }

    public UserResponseDTO create(UserRequestDTO dto) {
        User user = UserMapper.toEntity(dto);
        return UserMapper.toResponseDTO(userRepository.save(user));
    }

    public void delete(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(User.class, id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public UserResponseDTO update(Long id, UserRequestDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(User.class, id));

        user.setEmail(dto.email());
        user.setPassword(dto.password());

        return UserMapper.toResponseDTO(userRepository.save(user));
    }
}