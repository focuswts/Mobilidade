package com.bagatim.mobility.services;

import com.bagatim.mobility.dtos.buslines.BusLineDTO;
import com.bagatim.mobility.dtos.buslines.BusLinesDTO;
import com.bagatim.mobility.entities.BusLineEntity;
import com.bagatim.mobility.feignControllers.PoaTransporteController;
import com.bagatim.mobility.repositories.BusLineRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MobilityServiceImpl implements MobilityService {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private PoaTransporteController poaTransporteController;

    @Autowired
    private BusLineRepository busLineRepository;

    @Override
    public BusLinesDTO findBusLines() {

        final ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = poaTransporteController.findBusLines();

        try {

            List<BusLineDTO> busLines = busLinesFromPOAClientToDTO(objectMapper, jsonResponse);
            saveBusLinesIfNotExists(objectMapper, busLines);

            return BusLinesDTO.builder()
                    .busLines(busLines)
                    .build();

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;

    }

    private List<BusLineDTO> busLinesFromPOAClientToDTO(ObjectMapper objectMapper, String jsonResponse) throws JsonProcessingException {
        return objectMapper.readValue(jsonResponse, new TypeReference<List<BusLineDTO>>() {
        });
    }

    @Override
    public List<BusLineDTO> findBusLinesByNome(String nome) {

        try {

            List<Optional<BusLineEntity>> busLineList = busLineRepository.findByNome(nome);

            if (busLineList.isEmpty()) {
                return null;
            }

            return busLineList.stream()
                    .map(busLineEntity -> objectMapper
                            .convertValue(busLineEntity.get(), BusLineDTO.class))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    private BusLineEntity saveBusLineIfNotExists(ObjectMapper objectMapper, BusLineDTO busLine) {
        if (busLineRepository.findById(Integer.parseInt(busLine.getId())).isEmpty()) {
            BusLineEntity busLineEntity = objectMapper.convertValue(busLine, BusLineEntity.class);
            return busLineRepository.save(busLineEntity);
        } else {
            return busLineRepository.findById(Integer.parseInt(busLine.getId())).get();
        }
    }

    private void saveBusLinesIfNotExists(ObjectMapper objectMapper, List<BusLineDTO> busLines) {
        busLines.stream()
                .forEach(busLineDTO -> {
                    if (busLineRepository.findById(Integer.parseInt(busLineDTO.getId())).isEmpty()) {
                        log.info("MobilityServiceImpl.saveBusLineIfNotExists - saving bus line");
                        BusLineEntity busLine = objectMapper.convertValue(busLineDTO, BusLineEntity.class);
                        busLineRepository.save(busLine);
                    }
                });
    }




}
