package com.mtjb.examples.services;

import com.mtjb.examples.dto.CarDto;
import com.mtjb.examples.entities.Car;

import java.util.List;

public interface CarService {

    Car create(CarDto dto);

    List<Car> findAll();

    List<Car> findAllCarsByEntityGraph();
}
