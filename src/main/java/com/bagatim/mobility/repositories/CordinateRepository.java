package com.bagatim.mobility.repositories;

import com.bagatim.mobility.entities.CordinateEntity;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CordinateRepository extends JpaRepository<CordinateEntity, String> {

    @Modifying
    @Query(value = "DELETE FROM cordinate WHERE transport_unit_code = ?1", nativeQuery = true)
    void deleteAllBytransportunitcode(int transportUnitCode);

    Optional<List<CordinateEntity>> findAllBytransportunitcode(int transportUnitCode);

    String HAVERSINE_FORMULA = "(6371 * acos(cos(radians(:latitude)) * cos(radians(lat)) *" +
            " cos(radians(lng) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(lat))))";

    @Modifying
    @Query(value = "SELECT * FROM cordinate WHERE " + HAVERSINE_FORMULA + " < :distance ORDER BY " + HAVERSINE_FORMULA + " DESC", nativeQuery = true)
    Optional<List<CordinateEntity>> findCordinatesWithInDistance(@Param("latitude") double latitude, @Param("longitude") double longitude, @Param("distance") double distance);

}
