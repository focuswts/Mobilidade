package com.bagatim.mobility.services;

import com.bagatim.mobility.dtos.buslines.CordinateDTO;
import com.bagatim.mobility.dtos.buslines.ItineraryDTO;
import com.bagatim.mobility.entities.CordinateEntity;
import com.bagatim.mobility.entities.ItineraryEntity;
import com.bagatim.mobility.feignControllers.PoaTransporteController;
import com.bagatim.mobility.repositories.CordinateRepository;
import com.bagatim.mobility.repositories.ItineraryRepository;
import com.bagatim.mobility.tools.ItineraryTool;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @Transactional
    public ItineraryDTO findItineraryAndSaveOnDatabaseIfNotExists(int transportUnitCode) {

        String jsonResponse = poaTransporteController.findItinerary(transportUnitCode);
        ItineraryDTO itineraryDTO = itineraryTool.createItineraryFromJsonObject(jsonResponse);
        ItineraryEntity itineraryEntity = itineraryTool.convertItineraryDTOToItineraryEntity(itineraryDTO);
        Optional<ItineraryEntity> existingItinerary = itineraryRepository.findByIdLinha(transportUnitCode);

        if (existingItinerary.isEmpty() || !Objects.equals(existingItinerary.get(), itineraryEntity)) {
            List<CordinateEntity> cordinateList = itineraryTool.convertCordinatesMapToList(transportUnitCode, itineraryDTO.getCordinatesList());
            itineraryEntity.setCordinatesList(cordinateList);

            if (cordinateRepository.findAllBytransportunitcode(transportUnitCode).isPresent()) {
                cordinateRepository.deleteAllBytransportunitcode(transportUnitCode);
            }

            cordinateRepository.saveAll(itineraryEntity.getCordinatesList());

            return itineraryTool.convertItineraryEntityToItineraryDTO(itineraryRepository.save(itineraryEntity));
        }

        return itineraryTool.convertItineraryEntityToItineraryDTO(itineraryRepository.findByIdLinha(transportUnitCode).get());
    }

    @Override
    @Transactional
    public HttpStatus deleteItinerary(int itineraryId) {

        Optional<ItineraryEntity> itineraryEntity = itineraryRepository.findByIdLinha(itineraryId);

        if (itineraryEntity.isEmpty()) {
            return null;
        }

        cordinateRepository.deleteAllBytransportunitcode(itineraryEntity.get().getIdLinha());
        itineraryRepository.delete(itineraryEntity.get());
        return HttpStatus.OK;
    }


    @Override
    @Transactional
    public ItineraryDTO createItinerary(ItineraryDTO itineraryDTO) {

        ItineraryEntity itineraryEntity = itineraryTool.convertItineraryDTOToItineraryEntity(itineraryDTO);
        itineraryEntity.setCordinatesList(itineraryTool.convertCordinatesMapToList(Integer.parseInt(itineraryDTO.getIdLinha()), itineraryDTO.getCordinatesList()));

        cordinateRepository.saveAll(itineraryEntity.getCordinatesList());
        return itineraryTool.convertItineraryEntityToItineraryDTO(itineraryRepository.save(itineraryEntity));

    }

    @Override
    @Transactional
    public ItineraryDTO updateItinerary(int transportUnitCode, ItineraryDTO itineraryDTO) {

        ItineraryEntity itineraryEntity = itineraryTool.convertItineraryDTOToItineraryEntity(itineraryDTO);
        itineraryEntity.setCordinatesList(itineraryTool.convertCordinatesMapToList(Integer.parseInt(itineraryDTO.getIdLinha()), itineraryDTO.getCordinatesList()));
        Optional<ItineraryEntity> existingItinerary = itineraryRepository.findByIdLinha(transportUnitCode);

        if (existingItinerary.isEmpty()) {
            return null;
        }

        Optional<List<CordinateEntity>> cordinateEntityList = cordinateRepository.findAllBytransportunitcode(transportUnitCode);

        if (cordinateEntityList.isPresent()) {
            cordinateRepository.deleteAllBytransportunitcode(Integer.parseInt(itineraryDTO.getIdLinha()));
        }

        cordinateRepository.saveAll(itineraryEntity.getCordinatesList());
        return itineraryTool.convertItineraryEntityToItineraryDTO(itineraryRepository.save(itineraryEntity));

    }

    @Override
    public List<CordinateDTO> filterBusLines(double lat, double lng, double radius) {

        List<CordinateEntity> cordinateEntityList = itineraryTool.calculateDistanceInKilometer(lat, lng, radius);

        if (cordinateEntityList.isEmpty()) {
            return null;
        }

        return cordinateEntityList.stream().map(cordinateEntity -> {
            return itineraryTool.convertCordinateEntityToDTO(cordinateEntity);
        }).collect(Collectors.toList());

    }

}
