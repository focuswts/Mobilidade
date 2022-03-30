package com.bagatim.mobility.controllers;

import com.bagatim.mobility.dtos.buslines.BusLineDTO;
import com.bagatim.mobility.dtos.buslines.BusLinesDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface BusLinesController {

    ResponseEntity<BusLinesDTO> findBusLines();

    ResponseEntity<List<BusLineDTO>> findBusLinesByNome(String nome);

    ResponseEntity<?> deleteBusLine(String busLineId);

    ResponseEntity<BusLineDTO> updateBusLine(String busLineId, BusLineDTO body);

    ResponseEntity<BusLineDTO> createBusLine(BusLineDTO body);

}
