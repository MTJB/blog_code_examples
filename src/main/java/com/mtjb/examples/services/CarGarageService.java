package com.mtjb.examples.services;

import com.mtjb.examples.dto.CarGarageDto;
import com.mtjb.examples.entities.Car;
import com.mtjb.examples.entities.CarGarage;
import com.mtjb.examples.exceptions.NotFoundException;

import java.util.List;

public interface CarGarageService {

    List<CarGarage> findAll();

    CarGarage findById(Long id) throws NotFoundException;

    List<Car> findCarsByMake(CarGarageDto dto, String make);

    CarGarage create(CarGarageDto dto);
}
