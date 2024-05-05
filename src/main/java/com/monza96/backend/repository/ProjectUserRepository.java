package com.monza96.backend.repository;

import com.monza96.backend.domain.ProjectUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectUserRepository extends JpaRepository<ProjectUser, Long> {
    List<ProjectUser> findAllByProjectId(Long projectId);

    Optional<ProjectUser> findByProjectIdAndId(Long projectId, Long id);
}
