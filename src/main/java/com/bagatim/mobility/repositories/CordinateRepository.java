package com.bagatim.mobility.repositories;

import com.bagatim.mobility.entities.CordinateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CordinateRepository extends JpaRepository<CordinateEntity, String> {
}
