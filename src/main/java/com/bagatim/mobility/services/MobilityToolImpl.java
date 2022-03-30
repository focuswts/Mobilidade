package com.bagatim.mobility.services;

import com.bagatim.mobility.dtos.buslines.CordinateDTO;
import com.bagatim.mobility.dtos.buslines.ItineraryDTO;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MobilityToolImpl implements MobilityTool {

    @Override
    public ItineraryDTO createItineraryFromJsonObject(String jsonResponse) {
        JSONObject jsonObject = new JSONObject(jsonResponse);
        String idlinha = jsonObject.get("idlinha").toString();
        String nome = String.valueOf(jsonObject.get("nome"));
        String codigo = String.valueOf(jsonObject.get("codigo"));
        Map<String, CordinateDTO> cordinateMap = new HashMap<>();
        this.createCordinateMap(cordinateMap, jsonObject);

        return new ItineraryDTO(idlinha, nome, codigo, cordinateMap);

    }

    @Override
    public void createCordinateMap(Map<String, CordinateDTO> cordinateMap, JSONObject jsonObject) {

        jsonObject.names().forEach(index ->
                {
                    getCoordinateWhenIndexIsInteger(jsonObject, index, cordinateMap);
                }
        );

    }

    @Override
    public void getCoordinateWhenIndexIsInteger(JSONObject jsonObject, Object index, Map<String, CordinateDTO> cordinateMap) {

        if (index.toString().matches("^[0-9]*[1-9][0-9]*$")) {
            String lat = (String) jsonObject.getJSONObject(index.toString()).get("lat");
            String lng = (String) jsonObject.getJSONObject(index.toString()).get("lng");
            CordinateDTO cordinateDTO = new CordinateDTO(lat, lng);
            cordinateMap.put(index.toString(), cordinateDTO);
        }

    }

}
