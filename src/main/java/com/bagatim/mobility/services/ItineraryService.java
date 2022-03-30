package com.bagatim.mobility.services;

import com.bagatim.mobility.dtos.buslines.CordinateDTO;
import com.bagatim.mobility.dtos.buslines.ItineraryDTO;
import org.springframework.http.HttpStatus;

import javax.transaction.Transactional;
import java.util.List;

public interface ItineraryService {

    ItineraryDTO findItineraryAndSaveOnDatabaseIfNotExists(int transportUnitCode);

    HttpStatus deleteItinerary(int itineraryId);

    @Transactional
    ItineraryDTO createItinerary(ItineraryDTO itineraryDTO);

    @Transactional
    ItineraryDTO updateItinerary(int transportUnitCode, ItineraryDTO itineraryDTO);

    List<CordinateDTO> filterBusLines(double lat, double lng, double radius);

}
