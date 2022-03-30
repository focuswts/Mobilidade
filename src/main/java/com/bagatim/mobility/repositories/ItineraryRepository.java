package com.bagatim.mobility.repositories;

import com.bagatim.mobility.entities.ItineraryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItineraryRepository extends JpaRepository<ItineraryEntity, String> {

    Optional<ItineraryEntity> findByIdLinha(int idLinha);

}
