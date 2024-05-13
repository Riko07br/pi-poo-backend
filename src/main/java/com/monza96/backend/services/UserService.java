package com.monza96.backend.services;

import com.monza96.backend.domain.User;
import com.monza96.backend.domain.dtos.UserRequestDTO;
import com.monza96.backend.domain.dtos.UserResponseDTO;
import com.monza96.backend.domain.mappers.UserMapper;
import com.monza96.backend.repository.UserRepository;
import com.monza96.backend.resources.QueryParams;
import com.monza96.backend.services.exceptions.DatabaseException;
import com.monza96.backend.services.exceptions.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<UserResponseDTO> findAll(QueryParams queryParams) {
        return userRepository.findAll(queryParams.getPageable()).map(x -> UserMapper.toResponseDTO(x));
    }

    public UserResponseDTO findById(Long id) {
        User user = findEntityById(id);
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
        User user = findEntityById(id);

        user.setEmail(dto.email());
        user.setPassword(dto.password());

        return UserMapper.toResponseDTO(userRepository.save(user));
    }

    public Page<UserResponseDTO> findUsersByProjectId(Long projectId, QueryParams queryParams) {
        return userRepository.findByProjectUsersProjectId(projectId, queryParams.getPageable())
                .map(x -> UserMapper.toResponseDTO(x));
    }

    User findEntityById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(User.class, id));
    }

    User findEntityByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(User.class, 0L));
    }


}
