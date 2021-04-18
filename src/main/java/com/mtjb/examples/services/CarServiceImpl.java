package com.mtjb.examples.services;

import com.mtjb.examples.dto.CarDto;
import com.mtjb.examples.entities.Car;
import com.mtjb.examples.repositories.CarRepository;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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

    @Override
    public List<Car> findAll() {
        return carRepository.findAllCars();
    }

    @Override
    public List<Car> findAllCarsByEntityGraph() {
        return carRepository.findAll();
    }
}
