package com.bagatim.mobility.dtos.buslines;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItineraryDTO {

    @JsonProperty("idlinha")
    private String idLinha;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("codigo")
    private String codigo;

    private Map<String, CordinateDTO> cordinatesList;

}
