package com.bagatim.mobility.services;

import com.bagatim.mobility.dtos.buslines.BusLineDTO;
import com.bagatim.mobility.entities.BusLineEntity;
import com.bagatim.mobility.repositories.BusLineRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BusLineServiceTests {

    @InjectMocks
    BusLineService busLineService = new BusLineServiceImpl();

    @Mock
    ObjectMapper objectMapper;

    @Mock
    BusLineRepository busLineRepository;

    @Test
    @DisplayName("Should create bus line success test")
    void shouldCreateBusLineSuccessTest() {

        BusLineDTO busLineDTO = createBusLineDTO();
        BusLineEntity busLineEntity = createBusLineEntity();

        when(objectMapper.convertValue(busLineDTO, BusLineEntity.class)).thenReturn(
                busLineEntity
        );

        when(busLineRepository.save(any())).thenReturn(
                busLineEntity
        );

        when(objectMapper.convertValue(busLineEntity, BusLineDTO.class)).thenReturn(
                busLineDTO
        );

        BusLineDTO response = busLineService.createBusLine(busLineDTO);

        assertNotNull(response);
        assertEquals("1", response.getId());
        assertEquals("codigo", response.getCodigo());
        assertEquals("nome", response.getNome());

    }

    @Test
    @DisplayName("Should update bus line success test")
    void shouldUpdateBusLineSuccessTest() {

        BusLineDTO busLineDTO = createBusLineDTO();
        BusLineDTO updatedBusLineDTO = createBusLineDTO();
        updatedBusLineDTO.setCodigo("codigo2");

        BusLineEntity busLineEntity = createBusLineEntity();

        when(busLineRepository.findById(anyInt())).thenReturn(
                Optional.of(busLineEntity)
        );

        when(objectMapper.convertValue(busLineEntity, BusLineDTO.class)).thenReturn(
                updatedBusLineDTO
        );

        when(busLineRepository.save(any())).thenReturn(
                busLineEntity
        );

        when(objectMapper.convertValue(busLineEntity, BusLineDTO.class)).thenReturn(
                updatedBusLineDTO
        );

        BusLineDTO response = busLineService.updateBusLine(String.valueOf(1), busLineDTO);

        assertNotNull(response);
        assertEquals("1", response.getId());
        assertEquals("codigo2", response.getCodigo());
        assertEquals("nome", response.getNome());

    }

    @Test
    @DisplayName("Should update bus line fail test")
    void shouldUpdateBusLineFailTest() {

        BusLineDTO busLineDTO = createBusLineDTO();
        BusLineDTO updatedBusLineDTO = createBusLineDTO();
        updatedBusLineDTO.setCodigo("codigo2");

        BusLineEntity busLineEntity = createBusLineEntity();

        when(busLineRepository.findById(anyInt())).thenReturn(
                Optional.empty()
        );

        BusLineDTO response = busLineService.updateBusLine(String.valueOf(1), busLineDTO);

        assertNull(response);

    }

    @Test
    @DisplayName("Should create bus line fail test")
    void shouldCreateBusLineFailTest() {

        BusLineDTO busLineDTO = createBusLineDTO();

        BusLineDTO response = busLineService.createBusLine(busLineDTO);

        assertNull(response);

    }

    @Test
    @DisplayName("Should get bus line success test")
    void shouldGetBusLineSuccessTest() {

        BusLineDTO busLineDTO = createBusLineDTO();
        BusLineEntity busLineEntity = createBusLineEntity();

        when(busLineRepository.getById(any())).thenReturn(
                busLineEntity
        );

        when(objectMapper.convertValue(busLineEntity, BusLineDTO.class)).thenReturn(
                busLineDTO
        );

        BusLineDTO response = busLineService.getBusLine("1");

        assertNotNull(response);
        assertEquals("1", response.getId());
        assertEquals("codigo", response.getCodigo());
        assertEquals("nome", response.getNome());

    }

    @Test
    @DisplayName("Should delete bus line success test")
    void shouldDeleteBusLineSuccessTest() {

        BusLineDTO busLineDTO = createBusLineDTO();
        BusLineDTO updatedBusLineDTO = createBusLineDTO();
        updatedBusLineDTO.setCodigo("codigo2");

        BusLineEntity busLineEntity = createBusLineEntity();

        when(busLineRepository.findById(anyInt())).thenReturn(
                Optional.of(busLineEntity)
        );

        doNothing().when(busLineRepository).deleteById(1);

        when(objectMapper.convertValue(busLineEntity, BusLineDTO.class)).thenReturn(
                updatedBusLineDTO
        );

        HttpStatus response = busLineService.deleteBusLine(String.valueOf(1));

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response);

    }

    @Test
    @DisplayName("Should delete bus line fail test")
    void shouldDeleteBusLineFailTest() {

        BusLineDTO updatedBusLineDTO = createBusLineDTO();
        updatedBusLineDTO.setCodigo("codigo2");

        when(busLineRepository.findById(anyInt())).thenReturn(
                Optional.empty()
        );

        HttpStatus response = busLineService.deleteBusLine(String.valueOf(1));
        assertNull(response);

    }


    private BusLineEntity createBusLineEntity() {
        BusLineEntity busLineEntity = new BusLineEntity();
        busLineEntity.setId(1);
        busLineEntity.setNome("nome");
        busLineEntity.setCodigo("codigo");
        return busLineEntity;
    }

    private BusLineDTO createBusLineDTO() {
        BusLineDTO busLineDTO = new BusLineDTO();
        busLineDTO.setId("1");
        busLineDTO.setCodigo("codigo");
        busLineDTO.setNome("nome");
        return busLineDTO;
    }


}
