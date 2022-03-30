package com.bagatim.mobility.services;

import com.bagatim.mobility.dtos.buslines.CordinateDTO;
import com.bagatim.mobility.dtos.buslines.ItineraryDTO;
import org.json.JSONObject;

import java.util.Map;

public interface MobilityTool {

    ItineraryDTO createItineraryFromJsonObject(String jsonResponse);

    void createCordinateMap(Map<String, CordinateDTO> cordinateMap, JSONObject jsonObject);

    void getCoordinateWhenIndexIsInteger(JSONObject jsonObject, Object index, Map<String, CordinateDTO> cordinateMap);

}
