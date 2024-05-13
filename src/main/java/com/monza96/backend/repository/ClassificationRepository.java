package com.monza96.backend.repository;

import com.monza96.backend.domain.Classification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClassificationRepository extends JpaRepository<Classification, Long> {
    Page<Classification> findByProjectId(Long projectId, Pageable pageable);

    Optional<Classification> findByProjectIdAndId(Long projectId, Long id);
}
