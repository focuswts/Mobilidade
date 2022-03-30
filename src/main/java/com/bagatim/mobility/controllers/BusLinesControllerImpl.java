package com.bagatim.mobility.controllers;

import com.bagatim.mobility.dtos.buslines.BusLineDTO;
import com.bagatim.mobility.dtos.buslines.BusLinesDTO;
import com.bagatim.mobility.services.BusLineService;
import com.bagatim.mobility.services.MobilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/busLines")
public class BusLinesControllerImpl implements BusLinesController {

    @Autowired
    private MobilityService mobilityService;

    @Autowired
    private BusLineService busLineService;

    @Override
    @GetMapping("/findAll")
    public ResponseEntity<BusLinesDTO> findBusLines() {

        return new ResponseEntity<>(mobilityService.findBusLines(), HttpStatus.OK);

    }

    @Override
    @GetMapping("/find")
    public ResponseEntity<List<BusLineDTO>> findBusLinesByNome(@RequestParam String nome) {

        List<BusLineDTO> response = mobilityService.findBusLinesByNome(nome);

        if (Objects.isNull(response)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/create")
    public ResponseEntity<BusLineDTO> createBusLine(@RequestBody BusLineDTO body) {

        BusLineDTO response = busLineService.createBusLine(body);

        if (Objects.isNull(response)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PatchMapping("/update/{busLineId}")
    public ResponseEntity<BusLineDTO> updateBusLine(@PathVariable String busLineId, @RequestBody BusLineDTO body) {

        BusLineDTO response = busLineService.updateBusLine(busLineId, body);

        if (Objects.isNull(response)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @DeleteMapping("/delete/{busLineId}")
    public ResponseEntity<?> deleteBusLine(@PathVariable String busLineId) {

        HttpStatus response = busLineService.deleteBusLine(busLineId);

        if (Objects.isNull(response)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }



}
