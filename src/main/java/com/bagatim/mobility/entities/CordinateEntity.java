package com.bagatim.mobility.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "cordinate")
public class CordinateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "transport_unit_code", nullable = false)
    private int transportunitcode;

    @Column(name = "lat", nullable = false)
    private String lat;

    @Column(name = "lng", nullable = false)
    private String lng;

}
