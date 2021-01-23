package com.mtjb.examples.services;

import com.mtjb.examples.dto.CarGarageDto;
import com.mtjb.examples.entities.Car;
import com.mtjb.examples.entities.CarGarage;
import com.mtjb.examples.repositories.CarGarageRepository;
import javassist.NotFoundException;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
public class CarGarageServiceImpl implements CarGarageService {

    private final CarGarageRepository carGarageRepository;
    private final CarService carService;
    private final EntityManager entityManager;

    public CarGarageServiceImpl(CarGarageRepository carGarageRepository, @Lazy CarService carService, EntityManager entityManager) {
        this.carGarageRepository = carGarageRepository;
        this.carService = carService;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public CarGarage create(CarGarageDto dto) {
        CarGarage carGarage = new CarGarage();
        carGarage.setName(dto.getName());

        return carGarageRepository.save(carGarage);
    }

    @Override
    public List<CarGarage> findAll() {
        return (List<CarGarage>) carGarageRepository.findAll();
    }

    public boolean exists(Long id) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("Id must be specified");
        } else {
            return carGarageRepository.existsById(id);
        }
    }

    @Override
    @SneakyThrows
    public CarGarage findById(Long id) {

        if (id == null) {
            throw new IllegalArgumentException("Id must be specified");
        }

        return carGarageRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Car Garage with id {%d} not found", id)));
    }

    @Override
    @SneakyThrows
    public List<Car> findCarsByMake(CarGarageDto dto, String make) {
        if (this.exists(dto.getId())) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Car> query = cb.createQuery(Car.class);
            Root<Car> carRoot = query.from(Car.class);

            query.select(carRoot)
                    .where(cb.and(
                            cb.equal(carRoot.join("garage").get("name"), dto.getName()),
                            cb.equal(carRoot.get("make"), make)
                    ));

            return entityManager.createQuery(query)
                    .getResultList();
        } else {
            return Collections.emptyList();
        }
    }
}
