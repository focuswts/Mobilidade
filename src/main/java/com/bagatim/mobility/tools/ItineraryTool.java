package com.bagatim.mobility.tools;

import com.bagatim.mobility.dtos.buslines.CordinateDTO;
import com.bagatim.mobility.dtos.buslines.ItineraryDTO;
import com.bagatim.mobility.entities.CordinateEntity;
import com.bagatim.mobility.entities.ItineraryEntity;

import java.util.List;
import java.util.Map;

public interface ItineraryTool {

    ItineraryDTO createItineraryFromJsonObject(String jsonResponse);

    ItineraryDTO convertItineraryEntityToItineraryDTO(ItineraryEntity itineraryEntity);

    ItineraryEntity convertItineraryDTOToItineraryEntity(ItineraryDTO itineraryDTO);

    List<CordinateEntity> convertCordinatesMapToList(int transportUnitCode, Map<String, CordinateDTO> cordinatesMap);

    List<CordinateEntity> calculateDistanceInKilometer(double userLat, double userLng, double kilometersRadius);

    CordinateDTO convertCordinateEntityToDTO(CordinateEntity cordinateEntity);
}
