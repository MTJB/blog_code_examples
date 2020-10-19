package com.mtjb.examples.repositories;

import com.mtjb.examples.entities.CarGarage;
import org.springframework.data.repository.CrudRepository;

public interface CarGarageRepository extends CrudRepository<CarGarage, Long> {
}
