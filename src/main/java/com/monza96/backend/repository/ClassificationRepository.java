package com.monza96.backend.repository;

import com.monza96.backend.domain.Classification;
import com.monza96.backend.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassificationRepository extends JpaRepository<Classification, Long> {
}
