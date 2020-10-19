package com.mtjb.examples.services;

import com.mtjb.examples.entities.CarGarage;
import com.mtjb.examples.repositories.CarGarageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CarGarageService {

    @Autowired private CarGarageRepository carGarageRepository;

    @Transactional
    public CarGarage save(CarGarage carGarage) {
        return carGarageRepository.save(carGarage);
    }

    public List<CarGarage> findAll() {
        return (List<CarGarage>) carGarageRepository.findAll();
    }
}
