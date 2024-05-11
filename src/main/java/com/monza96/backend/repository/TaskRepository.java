package com.monza96.backend.repository;

import com.monza96.backend.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByProjectId(Long projectId);

    Optional<Task> findByProjectIdAndId(Long id, Long projectId);
}
