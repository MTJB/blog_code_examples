package com.mtjb.examples.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@NamedEntityGraph(
    name = "Car.garage",
    attributeNodes = {@NamedAttributeNode(value = "garage")}
)
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String make;
    private String model;
    private String registration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "garage_id", nullable = false)
    private CarGarage garage;
}
