package com.monza96.backend.resources;

import com.monza96.backend.domain.dtos.ProjectResponseDTO;
import com.monza96.backend.domain.dtos.TaskResponseDTO;
import com.monza96.backend.domain.dtos.UserRequestDTO;
import com.monza96.backend.domain.dtos.UserResponseDTO;
import com.monza96.backend.services.ProjectService;
import com.monza96.backend.services.TaskService;
import com.monza96.backend.services.UserService;
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

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserResource {
    private final UserService userService;
    private final ProjectService projectService;
    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> findAll(QueryParams queryParams) {
        Page<UserResponseDTO> users = userService.findAll(queryParams);
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
        UserResponseDTO responseDTO = userService.findById(id);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody UserRequestDTO userRequestDTO) throws URISyntaxException {
        UserResponseDTO responseDTO = userService.create(userRequestDTO);
        return ResponseEntity.created(new URI("/users/" + responseDTO.id())).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable Long id, @RequestBody UserRequestDTO requestDTO) {
        UserResponseDTO responseDTO = userService.update(id, requestDTO);
        return ResponseEntity.ok().body(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/projects")
    public ResponseEntity<Page<ProjectResponseDTO>> findProjects(@PathVariable Long id, QueryParams queryParams) {
        Page<ProjectResponseDTO> projects = projectService.findProjectsByUserId(id, queryParams);
        return ResponseEntity.ok().body(projects);
    }

    @GetMapping("/{id}/tasks")
    public ResponseEntity<Page<TaskResponseDTO>> findTasks(@PathVariable Long id, QueryParams queryParams) {
        Page<TaskResponseDTO> tasks = taskService.findTasksByUserId(id, queryParams);
        return ResponseEntity.ok().body(tasks);
    }

}
