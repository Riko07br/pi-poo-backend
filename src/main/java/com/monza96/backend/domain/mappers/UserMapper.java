package com.monza96.backend.domain.mappers;

import com.monza96.backend.domain.User;
import com.monza96.backend.domain.dtos.UserRequestDTO;
import com.monza96.backend.domain.dtos.UserResponseDTO;

public class UserMapper {
    public static UserResponseDTO toResponseDTO(User user) {
        return new UserResponseDTO(user.getId(), user.getEmail());
    }

    public static User toEntity(UserRequestDTO dto) {
        return new User(null, dto.email(), dto.password());
    }
}
