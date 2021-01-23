package com.mtjb.examples.services;

import com.mtjb.examples.dto.CarDto;
import com.mtjb.examples.entities.Car;
import javassist.NotFoundException;

public interface CarService {

    Car create(CarDto dto);

    Car findById(Long id) throws NotFoundException;
}
