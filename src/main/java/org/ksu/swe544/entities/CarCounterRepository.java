package org.ksu.swe544.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarCounterRepository extends JpaRepository<CarCounter, Long> {
}