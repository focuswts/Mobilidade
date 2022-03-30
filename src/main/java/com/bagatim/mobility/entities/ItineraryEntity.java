package com.bagatim.mobility.entities;

import com.bagatim.mobility.dtos.buslines.CordinateDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Map;

@Data
@Entity
@Table(name = "itinerary")
public class ItineraryEntity {

    @Id
    @Column(name = "idlinha", nullable = false)
    private int idLinha;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "codigo", nullable = false)
    private String codigo;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "id", cascade = CascadeType.ALL)
    private List<CordinateEntity> cordinatesList;

}
