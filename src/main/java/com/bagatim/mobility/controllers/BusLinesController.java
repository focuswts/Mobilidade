package com.bagatim.mobility.controllers;

import com.bagatim.mobility.dtos.buslines.BusLineDTO;
import com.bagatim.mobility.dtos.buslines.BusLinesDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BusLinesController {

    ResponseEntity<BusLinesDTO> findBusLines();

    ResponseEntity<List<BusLineDTO>> findBusLinesByNome(String nome);

    ResponseEntity<?> deleteBusLine(String busLineId);

    ResponseEntity<BusLineDTO> updateBusLine(String busLineId, BusLineDTO body);

    ResponseEntity<BusLineDTO> createBusLine(BusLineDTO body);

}
