package com.bagatim.mobility.services;

import com.bagatim.mobility.dtos.buslines.ItineraryDTO;
import org.springframework.http.HttpStatus;

public interface ItineraryService {

    ItineraryDTO findItineraryAndSaveOnDatabaseIfNotExists(int transportUnitCode);

    HttpStatus deleteItinerary(int itineraryId);

}
