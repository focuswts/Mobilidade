package com.bagatim.mobility.repositories;

import com.bagatim.mobility.entities.BusLineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BusLineRepository extends JpaRepository<BusLineEntity, Integer> {

    List<Optional<BusLineEntity>> findByNome(String nome);

}
