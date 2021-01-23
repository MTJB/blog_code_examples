package com.mtjb.examples.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String make;
    private String model;

    @ManyToOne
    @JoinColumn(name = "garage_id", nullable = false)
    private CarGarage garage;
}
