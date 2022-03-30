package com.bagatim.mobility.feignControllers;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "poaTransporte", url = "http://www.poatransporte.com.br/php/facades/process.php")
public interface PoaTransporteController {

    @GetMapping(value = "?a=nc&p=%&t=o")
    String findBusLines();

    @GetMapping(value = "?a=il&p={transportUnitId}")
    String findItinerary(@PathVariable int transportUnitId);

}
