package com.monza96.backend.repository;

import com.monza96.backend.domain.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findByProjectId(Long projectId, Pageable pageable);

    Optional<Task> findByProjectIdAndId(Long projectId, Long id);

    Page<Task> findByProjectUsersUserId(Long userId, Pageable pageable);
}
