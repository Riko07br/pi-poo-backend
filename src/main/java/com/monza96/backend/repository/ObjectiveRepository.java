package com.monza96.backend.repository;

import com.monza96.backend.domain.Objective;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ObjectiveRepository extends JpaRepository<Objective, Long> {
    Page<Objective> findByTaskProjectIdAndTaskId(Long projectId, Long taskId, Pageable pageable);

    Optional<Objective> findByTaskProjectIdAndTaskIdAndId(Long projectId, Long taskId, Long id);
}
