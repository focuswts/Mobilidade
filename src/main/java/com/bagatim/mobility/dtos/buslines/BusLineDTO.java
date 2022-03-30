package com.bagatim.mobility.dtos.buslines;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BusLineDTO {

    @JsonProperty("id")
    private String id;

    @JsonProperty("codigo")
    private String codigo;

    @JsonProperty("nome")
    private String nome;

}
