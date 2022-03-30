package com.bagatim.mobility.services;

import com.bagatim.mobility.dtos.buslines.CordinateDTO;
import com.bagatim.mobility.dtos.buslines.ItineraryDTO;
import com.bagatim.mobility.entities.CordinateEntity;
import com.bagatim.mobility.entities.ItineraryEntity;
import com.bagatim.mobility.feignControllers.PoaTransporteController;
import com.bagatim.mobility.repositories.CordinateRepository;
import com.bagatim.mobility.repositories.ItineraryRepository;
import com.bagatim.mobility.tools.ItineraryTool;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ItineraryServiceTests {

    @InjectMocks
    ItineraryService itineraryService = new ItineraryServiceImpl();

    @Mock
    PoaTransporteController poaTransporteController;

    @Mock
    ItineraryTool itineraryTool;

    @Mock
    ItineraryRepository itineraryRepository;

    @Mock
    CordinateRepository cordinateRepository;

    @Test
    @DisplayName("should save on database if the itinerary does not exists")
    void shouldFindItineraryAndSaveOnDatabaseIfExistsSuccesTest() {

        when(poaTransporteController.findItinerary(anyInt())).thenReturn(
                ""
        );

        when(itineraryTool.createItineraryFromJsonObject(anyString())).thenReturn(
                createItineraryDTO()
        );

        when(itineraryTool.convertItineraryDTOToItineraryEntity(
                createItineraryDTO()
        )).thenReturn(
                createItineraryEntity()
        );

        when(itineraryRepository.findByIdLinha(anyInt())).thenReturn(
                Optional.of(createItineraryEntity())
        );

        when(itineraryTool.convertCordinatesMapToList(anyInt(), any())).thenReturn(
                createCordinateEntityList()
        );

        when(itineraryTool.convertItineraryEntityToItineraryDTO(any())).thenReturn(
                createItineraryDTO()
        );

        ItineraryDTO response = itineraryService.findItineraryAndSaveOnDatabaseIfNotExists(2256);

        assertNotNull(response);
        assertEquals("1234", response.getIdLinha());
        assertEquals("nome", response.getNome());
        assertEquals("codigo", response.getCodigo());
        assertEquals(1, response.getCordinatesList().size());

    }

    @Test
    void shouldFindItineraryAndSaveOnDatabaseIfNotExistsSuccesTest() {

        when(poaTransporteController.findItinerary(anyInt())).thenReturn(
                ""
        );

        when(itineraryTool.createItineraryFromJsonObject(anyString())).thenReturn(
                createItineraryDTO()
        );

        when(itineraryTool.convertItineraryDTOToItineraryEntity(
                createItineraryDTO()
        )).thenReturn(
                createItineraryEntity()
        );

        when(itineraryRepository.findByIdLinha(anyInt())).thenReturn(
                Optional.empty()
        );

        when(itineraryTool.convertCordinatesMapToList(anyInt(), any())).thenReturn(
                createCordinateEntityList()
        );

        when(itineraryTool.convertItineraryEntityToItineraryDTO(any())).thenReturn(
                createItineraryDTO()
        );

        ItineraryDTO response = itineraryService.findItineraryAndSaveOnDatabaseIfNotExists(2256);

        assertNotNull(response);
        assertEquals("1234", response.getIdLinha());
        assertEquals("nome", response.getNome());
        assertEquals("codigo", response.getCodigo());
        assertEquals(1, response.getCordinatesList().size());

    }

    @Test
    void shouldCreateItineraryIfNotExists() {

        when(itineraryTool.convertItineraryDTOToItineraryEntity(any())).thenReturn(
                createItineraryEntity()
        );

        when(itineraryTool.convertCordinatesMapToList(anyInt(), any())).thenReturn(
                createCordinateEntityList()
        );

        when(itineraryRepository.findById(anyString())).thenReturn(
                Optional.empty()
        );

        when(itineraryTool.convertItineraryEntityToItineraryDTO(any())).thenReturn(
                createItineraryDTO()
        );

        ItineraryDTO response = itineraryService.createItinerary(createItineraryDTO());

        assertNotNull(response);
        assertEquals("1234", response.getIdLinha());
        assertEquals("nome", response.getNome());
        assertEquals("codigo", response.getCodigo());
        assertEquals(1, response.getCordinatesList().size());

    }

    @Test
    void shouldReturnItineraryIfExists() {

        when(itineraryTool.convertItineraryDTOToItineraryEntity(any())).thenReturn(
                createItineraryEntity()
        );

        when(itineraryTool.convertCordinatesMapToList(anyInt(), any())).thenReturn(
                createCordinateEntityList()
        );

        when(itineraryRepository.findById(anyString())).thenReturn(
                Optional.of(createItineraryEntity())
        );

        when(itineraryTool.convertItineraryEntityToItineraryDTO(any())).thenReturn(
                createItineraryDTO()
        );

        ItineraryDTO response = itineraryService.createItinerary(createItineraryDTO());

        assertNotNull(response);
        assertEquals("1234", response.getIdLinha());
        assertEquals("nome", response.getNome());
        assertEquals("codigo", response.getCodigo());
        assertEquals(1, response.getCordinatesList().size());

    }

    @Test
    void shouldUpdateItinerary() {

        when(itineraryTool.convertItineraryDTOToItineraryEntity(any())).thenReturn(
                createItineraryEntity()
        );

        when(itineraryTool.convertCordinatesMapToList(anyInt(), any())).thenReturn(
                createCordinateEntityList()
        );

        when(itineraryRepository.findByIdLinha(anyInt())).thenReturn(
                Optional.of(createItineraryEntity())
        );

        when(cordinateRepository.findAllBytransportunitcode(anyInt())).thenReturn(
                Optional.empty()
        );

        when(itineraryTool.convertItineraryEntityToItineraryDTO(any())).thenReturn(
                createItineraryDTO()
        );

        ItineraryDTO response = itineraryService.updateItinerary(1234, createItineraryDTO());

        assertNotNull(response);
        assertEquals("1234", response.getIdLinha());
        assertEquals("nome", response.getNome());
        assertEquals("codigo", response.getCodigo());
        assertEquals(1, response.getCordinatesList().size());

    }

    @Test
    void shouldUpdateItineraryWhenNotExists() {

        when(itineraryTool.convertItineraryDTOToItineraryEntity(any())).thenReturn(
                createItineraryEntity()
        );

        when(itineraryTool.convertCordinatesMapToList(anyInt(), any())).thenReturn(
                createCordinateEntityList()
        );

        when(itineraryRepository.findByIdLinha(anyInt())).thenReturn(
                Optional.of(createItineraryEntity())
        );

        ItineraryDTO response = itineraryService.updateItinerary(1234, createItineraryDTO());

        assertNull(response);

    }

    @Test
    void shouldFilterBusLinesSuccessTest() {

        when(itineraryTool.calculateDistanceInKilometer(anyDouble(), anyDouble(), anyDouble())).thenReturn(
                createCordinateEntityList()
        );

        when(itineraryTool.convertCordinateEntityToDTO(any())).thenReturn(
                createCordinateDTO()
        );

        List<CordinateDTO> response = itineraryService.filterBusLines(-30.07649257845100000, -51.20062311462900000, 2);

        assertNotNull(response);
        assertEquals("-30.07649257845100000", response.get(0).getLat());
        assertEquals("-51.20062311462900000", response.get(0).getLng());
    }

    @Test
    void shouldFilterBusLinesFailTest() {

        when(itineraryTool.calculateDistanceInKilometer(anyDouble(), anyDouble(), anyDouble())).thenReturn(
                new ArrayList<>()
        );

        List<CordinateDTO> response = itineraryService.filterBusLines(-30.07649257845100000, -51.20062311462900000, 2);

        assertNull(response);
    }

    @Test
    void shouldDeleteItinerarySuccessTest() {

        when(itineraryRepository.findByIdLinha(anyInt())).thenReturn(
                Optional.of(createItineraryEntity())
        );

        doNothing().when(cordinateRepository).deleteAllBytransportunitcode(anyInt());
        doNothing().when(itineraryRepository).delete(any());

        HttpStatus response = itineraryService.deleteItinerary(1234);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response);

    }

    @Test
    void shouldDeleteItineraryFailTest() {

        when(itineraryRepository.findByIdLinha(anyInt())).thenReturn(
                Optional.empty()
        );

        HttpStatus response = itineraryService.deleteItinerary(1234);

        assertNull(response);

    }

    private ItineraryEntity createItineraryEntity() {
        ItineraryEntity itineraryEntity = new ItineraryEntity();
        itineraryEntity.setIdLinha(1234);
        itineraryEntity.setNome("nome");
        itineraryEntity.setCodigo("codigo");
        itineraryEntity.setCordinatesList(createCordinateEntityList());

        return itineraryEntity;
    }

    private ItineraryDTO createItineraryDTO() {
        ItineraryDTO itineraryDTO = new ItineraryDTO();
        itineraryDTO.setIdLinha("1234");
        itineraryDTO.setNome("nome");
        itineraryDTO.setCodigo("codigo");
        itineraryDTO.setCordinatesList(createCordinateDTOMap());

        return itineraryDTO;
    }

    private Map<String, CordinateDTO> createCordinateDTOMap() {
        Map<String, CordinateDTO> cordinateDTOMap = new HashMap<>();
        cordinateDTOMap.put("1", createCordinateDTO());
        return cordinateDTOMap;
    }

    private List<CordinateEntity> createCordinateEntityList() {
        List<CordinateEntity> cordinateEntityList = new ArrayList<>();
        cordinateEntityList.add(createCordinateEntity());

        return cordinateEntityList;
    }

    private CordinateEntity createCordinateEntity() {
        CordinateEntity cordinateEntity = new CordinateEntity();
        cordinateEntity.setId(1);
        cordinateEntity.setLat("-30.07649257845100000");
        cordinateEntity.setLng("-51.20062311462900000");
        cordinateEntity.setTransportunitcode(1234);

        return cordinateEntity;
    }

    private CordinateDTO createCordinateDTO() {
        CordinateDTO cordinateDTO = new CordinateDTO();
        cordinateDTO.setLat("-30.07649257845100000");
        cordinateDTO.setLng("-51.20062311462900000");
        return cordinateDTO;
    }


}
