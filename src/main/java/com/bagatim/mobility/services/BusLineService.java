package com.bagatim.mobility.services;

import com.bagatim.mobility.dtos.buslines.BusLineDTO;
import com.bagatim.mobility.entities.BusLineEntity;
import org.springframework.http.HttpStatus;

public interface BusLineService {

    BusLineDTO getBusLine(String id);

    BusLineDTO createBusLine(BusLineDTO busLine);

    BusLineDTO updateBusLine(String busLineId, BusLineDTO busLine);

    HttpStatus deleteBusLine(String busLineId);

}
