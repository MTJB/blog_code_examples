package com.mtjb.examples.repositories;

import com.mtjb.examples.entities.Car;
import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<Car, Long> {
}
