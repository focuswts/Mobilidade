package com.bagatim.mobility.tools;

import com.bagatim.mobility.dtos.buslines.CordinateDTO;
import com.bagatim.mobility.dtos.buslines.ItineraryDTO;
import com.bagatim.mobility.entities.CordinateEntity;
import com.bagatim.mobility.entities.ItineraryEntity;
import com.bagatim.mobility.repositories.CordinateRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ItineraryToolsImpl implements ItineraryTool {

    @Autowired
    CordinateRepository cordinateRepository;

    public ItineraryDTO createItineraryFromJsonObject(String jsonResponse) {

        JSONObject jsonObject = new JSONObject(jsonResponse);
        String idlinha = jsonObject.get("idlinha").toString();
        String nome = String.valueOf(jsonObject.get("nome"));
        String codigo = String.valueOf(jsonObject.get("codigo"));
        Map<String, CordinateDTO> cordinateMap = new HashMap<>();
        createCordinateMap(cordinateMap, jsonObject);

        return new ItineraryDTO(idlinha, nome, codigo, cordinateMap);
    }

    public void createCordinateMap(Map<String, CordinateDTO> cordinateMap, JSONObject jsonObject) {
        jsonObject.names().forEach(index -> {
            getCoordinateWhenIndexIsInteger(jsonObject, index, cordinateMap);
        });
    }

    public void getCoordinateWhenIndexIsInteger(JSONObject jsonObject, Object index, Map<String, CordinateDTO> cordinateMap) {
        if (index.toString().matches("^[0-9]*[1-9][0-9]*$")) {
            String lat = (String) jsonObject.getJSONObject(index.toString()).get("lat");
            String lng = (String) jsonObject.getJSONObject(index.toString()).get("lng");
            CordinateDTO cordinateDTO = new CordinateDTO(lat, lng);
            cordinateMap.put(index.toString(), cordinateDTO);
        }
    }

    @Override
    public ItineraryEntity convertItineraryDTOToItineraryEntity(ItineraryDTO itineraryDTO) {
        ItineraryEntity itineraryEntity = new ItineraryEntity();
        itineraryEntity.setIdLinha(Integer.parseInt(itineraryDTO.getIdLinha()));
        itineraryEntity.setNome(itineraryDTO.getNome());
        itineraryEntity.setCodigo(itineraryDTO.getCodigo());
        return itineraryEntity;
    }

    public List<CordinateEntity> convertCordinatesMapToList(int transportUnitCode, Map<String, CordinateDTO> cordinatesMap) {
        List<CordinateEntity> cordinateEntityList = new ArrayList<>();

        cordinatesMap.forEach((key, cordinateDTO) -> {
            CordinateEntity cordinateEntity = new CordinateEntity();
            cordinateEntity.setTransportunitcode(transportUnitCode);
            cordinateEntity.setLat(cordinateDTO.getLat());
            cordinateEntity.setLng(cordinateDTO.getLng());

            cordinateEntityList.add(cordinateEntity);
        });

        return cordinateEntityList;
    }

    @Override
    public ItineraryDTO convertItineraryEntityToItineraryDTO(ItineraryEntity itineraryEntity) {
        ItineraryDTO itineraryDTO = new ItineraryDTO();
        itineraryDTO.setIdLinha(String.valueOf(itineraryEntity.getIdLinha()));
        itineraryDTO.setCodigo(itineraryEntity.getCodigo());
        itineraryDTO.setNome(itineraryEntity.getNome());
        itineraryDTO.setCordinatesList(convertCordinatesListToMap(itineraryEntity.getCordinatesList()));

        return itineraryDTO;
    }

    public Map<String, CordinateDTO> convertCordinatesListToMap(List<CordinateEntity> cordinateEntityList) {
        Map<String, CordinateDTO> cordinateDTOMap = new HashMap<>();
        final int[] key = {0};

        cordinateEntityList.stream().forEach(cordinateEntity -> {
            CordinateDTO cordinateDTO = new CordinateDTO();
            cordinateDTO.setLat(cordinateEntity.getLat());
            cordinateDTO.setLng(cordinateEntity.getLng());
            cordinateDTOMap.put(String.valueOf(key[0]++), cordinateDTO);
        });

        return cordinateDTOMap;
    }

    @Override
    public List<CordinateEntity> calculateDistanceInKilometer(double userLat, double userLng, double kilometersRadius) {

        Optional<List<CordinateEntity>> cordinateEntityList = cordinateRepository.findCordinatesWithInDistance(userLat, userLng, kilometersRadius);

        return cordinateEntityList.orElse(null);

    }


    @Override
    public CordinateDTO convertCordinateEntityToDTO(CordinateEntity cordinateEntity) {
        CordinateDTO cordinateDTO = new CordinateDTO();
        cordinateDTO.setLng(cordinateEntity.getLng());
        cordinateDTO.setLat(cordinateEntity.getLat());
        return cordinateDTO;
    }


}
