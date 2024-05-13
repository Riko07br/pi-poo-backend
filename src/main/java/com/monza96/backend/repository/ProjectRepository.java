package com.monza96.backend.repository;

import com.monza96.backend.domain.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Page<Project> findByProjectUsersUserEmail(String email, Pageable pageable);

    Page<Project> findByProjectUsersUserId(Long userId, Pageable pageable);
}
