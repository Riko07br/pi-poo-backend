package com.monza96.backend.repository;

import com.monza96.backend.domain.Objective;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObjectiveRepository extends JpaRepository<Objective, Long> {
}
