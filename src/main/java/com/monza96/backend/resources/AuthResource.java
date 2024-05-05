package com.monza96.backend.resources;

import com.monza96.backend.domain.dtos.UserRequestDTO;
import com.monza96.backend.domain.dtos.UserResponseDTO;
import com.monza96.backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthResource {

    private final UserService userService;

    @GetMapping("/login")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok().body("Successful");
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDTO> signup(@RequestBody UserRequestDTO userRequestDTO) throws URISyntaxException {
        UserResponseDTO responseDTO = userService.create(userRequestDTO);
        return ResponseEntity.created(new URI("/auth/" + responseDTO.id())).body(responseDTO);
    }
}
