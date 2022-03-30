package com.bagatim.mobility.controllers;

import com.bagatim.mobility.dtos.buslines.ItineraryDTO;
import com.bagatim.mobility.services.ItineraryService;
import com.bagatim.mobility.services.MobilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/itinerary")
public class ItineraryControllerImpl implements ItineraryController {

    @Autowired
    MobilityService mobilityService;

    @Autowired
    ItineraryService itineraryService;

    @Override
    public ResponseEntity<ItineraryDTO> createItinerary(ItineraryDTO body) {
        return null;
    }

    @Override
    public ResponseEntity<ItineraryDTO> updateItinerary(ItineraryDTO body) {
        return null;
    }

    @Override
    @GetMapping("/findItinerary/{transportUnitCode}")
    public ResponseEntity<ItineraryDTO> findItinerary(@PathVariable int transportUnitCode) {

        ItineraryDTO response = itineraryService.findItineraryAndSaveOnDatabaseIfNotExists(transportUnitCode);

        if (Objects.isNull(response)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @DeleteMapping("/delete/{itineraryId}")
    public ResponseEntity<?> deleteItinerary(@PathVariable int itineraryId) {

        HttpStatus response = itineraryService.deleteItinerary(itineraryId);

        if (Objects.isNull(response)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }

}
