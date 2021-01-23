package com.mtjb.examples.services;

import com.mtjb.examples.dto.CarDto;
import com.mtjb.examples.entities.Car;
import com.mtjb.examples.repositories.CarRepository;
import javassist.NotFoundException;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarGarageService carGarageService;

    public CarServiceImpl(CarRepository carRepository, @Lazy CarGarageService carGarageService) {
        this.carRepository = carRepository;
        this.carGarageService = carGarageService;
    }

    @Override
    @SneakyThrows
    @Transactional
    public Car create(CarDto dto) {
        Car car = new Car();
        car.setMake(dto.getMake());
        car.setModel(dto.getModel());
        car.setGarage(carGarageService.findById(dto.getGarage().getId()));

        return carRepository.save(car);
    }

    /**
     * @return The Car, if found. If not found {@link javassist.NotFoundException} thrown.
     */
    @Override
    @SneakyThrows
    public Car findById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Car with id {%d} not found", id)));
    }
}
