package com.bagatim.mobility.controllers;

import com.bagatim.mobility.dtos.buslines.ItineraryDTO;
import org.springframework.http.ResponseEntity;

public interface ItineraryController {

    ResponseEntity<ItineraryDTO> createItinerary(ItineraryDTO body);

    ResponseEntity<ItineraryDTO> updateItinerary(ItineraryDTO body);

    ResponseEntity<ItineraryDTO> findItinerary(int transportUnitCode);

}
