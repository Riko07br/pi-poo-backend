package com.monza96.backend.repository;

import com.monza96.backend.domain.ProjectUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectUserRepository extends JpaRepository<ProjectUser, Long> {
    Page<ProjectUser> findByProjectId(Long projectId, Pageable pageable);

    Optional<ProjectUser> findByProjectIdAndId(Long projectId, Long id);

    Page<ProjectUser> findByProjectIdAndTasksId(Long projectId, Long tasksId, Pageable pageable);

    Optional<ProjectUser> findByProjectIdAndTasksIdAndId(Long projectId, Long tasksId, Long id);
}
