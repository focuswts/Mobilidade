package com.bagatim.mobility.dtos.buslines;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusLinesDTO {

    List<BusLineDTO> busLines;

}
