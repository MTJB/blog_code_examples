package com.mtjb.examples.dto;

import com.mtjb.examples.entities.CarGarage;
import lombok.Data;

@Data
public class CarDto {
    private Long id;
    private String make;
    private String model;
    private CarGarage garage;
}
