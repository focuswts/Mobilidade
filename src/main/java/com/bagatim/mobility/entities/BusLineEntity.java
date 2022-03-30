package com.bagatim.mobility.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "busline")
@Entity
public class BusLineEntity {

    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "codigo", nullable = false)
    private String codigo;

    @Column(name = "nome", nullable = false)
    private String nome;

}
