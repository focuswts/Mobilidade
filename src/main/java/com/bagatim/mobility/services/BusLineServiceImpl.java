package com.bagatim.mobility.services;

import com.bagatim.mobility.dtos.buslines.BusLineDTO;
import com.bagatim.mobility.entities.BusLineEntity;
import com.bagatim.mobility.repositories.BusLineRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class BusLineServiceImpl implements BusLineService {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    BusLineRepository busLineRepository;

    @Override
    public BusLineDTO getBusLine(String id) {

        Optional<BusLineEntity> busLineEntity = Optional.of(busLineRepository.getById(Integer.parseInt(id)));
        return objectMapper.convertValue(busLineEntity, BusLineDTO.class);

    }

    @Override
    public BusLineDTO createBusLine(BusLineDTO busLine) {
        BusLineEntity busLineEntity = objectMapper.convertValue(busLine, BusLineEntity.class);
        return objectMapper.convertValue(busLineRepository.save(busLineEntity), BusLineDTO.class);
    }

    @Override
    public BusLineDTO updateBusLine(String busLineId, BusLineDTO body) {

        Optional<BusLineEntity> busLineEntity = busLineRepository.findById(Integer.parseInt(busLineId));
        BusLineEntity busLine = objectMapper.convertValue(body, BusLineEntity.class);

        if (busLineEntity.isPresent() && !Objects.equals(busLine, busLineEntity.get())) {
            body.setId(String.valueOf(busLineEntity.get().getId()));
            return objectMapper.convertValue(busLineRepository
                    .save(objectMapper.convertValue(body, BusLineEntity.class)), BusLineDTO.class);
        }

        return null;
    }

    @Override
    public HttpStatus deleteBusLine(String busLineId) {
        int busLineIntId = Integer.parseInt(busLineId);
        Optional<BusLineEntity> busLineEntity = busLineRepository.findById(busLineIntId);

        if (busLineEntity.isEmpty()) {
            return null;
        }

        busLineRepository.deleteById(busLineIntId);
        return HttpStatus.OK;
    }

}
