package com.bagatim.mobility.services;

import com.bagatim.mobility.dtos.buslines.ItineraryDTO;
import com.bagatim.mobility.entities.CordinateEntity;
import com.bagatim.mobility.entities.ItineraryEntity;
import com.bagatim.mobility.feignControllers.PoaTransporteController;
import com.bagatim.mobility.repositories.CordinateRepository;
import com.bagatim.mobility.repositories.ItineraryRepository;
import com.bagatim.mobility.tools.ItineraryTool;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ItineraryServiceImpl implements ItineraryService {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PoaTransporteController poaTransporteController;

    @Autowired
    ItineraryTool itineraryTool;

    @Autowired
    ItineraryRepository itineraryRepository;

    @Autowired
    CordinateRepository cordinateRepository;

    @Override
    @Transient
    public ItineraryDTO findItineraryAndSaveOnDatabaseIfNotExists(int transportUnitCode) {

        String jsonResponse = poaTransporteController.findItinerary(transportUnitCode);
        ItineraryDTO itineraryDTO = itineraryTool.createItineraryFromJsonObject(jsonResponse);
        ItineraryEntity itineraryEntity = itineraryTool.convertItineraryDTOToItineraryEntity(itineraryDTO);

        Optional<ItineraryEntity> existingItinerary = itineraryRepository.findByIdLinha(transportUnitCode);

        if (existingItinerary.isEmpty() || !Objects.equals(existingItinerary.get(), itineraryEntity)) {
//            //TODO salvar primeiro as cordenadas no banco
            List<CordinateEntity> cordinateList = itineraryTool.convertCordinatesMapToList(transportUnitCode, itineraryDTO.getCordinatesList());
            itineraryEntity.setCordinatesList(cordinateList);
            cordinateList.stream().forEach(
                    cordinateEntity -> cordinateRepository.save(cordinateEntity)
            );
//            cordinateRepository.saveAll(itineraryEntity.getCordinatesList());
            return itineraryTool.convertItineraryEntityToItineraryDTO(itineraryRepository.save(itineraryEntity));
        }

        return itineraryTool.convertItineraryEntityToItineraryDTO(itineraryRepository.findByIdLinha(transportUnitCode).get());
    }

    @Override
    public HttpStatus deleteItinerary(int itineraryId) {
        return null;
    }

//    public HttpStatus deleteItinerary(int itineraryId) {
//
//
//    }


}
