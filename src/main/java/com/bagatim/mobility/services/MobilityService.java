package com.bagatim.mobility.services;


import com.bagatim.mobility.dtos.buslines.BusLineDTO;
import com.bagatim.mobility.dtos.buslines.BusLinesDTO;

import java.util.List;

public interface MobilityService {

    BusLinesDTO findBusLines();

    List<BusLineDTO> findBusLinesByNome(String nome);


}
