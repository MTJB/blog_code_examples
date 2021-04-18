package com.mtjb.examples.repositories;

import com.mtjb.examples.entities.Car;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarRepository extends CrudRepository<Car, Long> {

    @Query("SELECT c FROM Car c JOIN FETCH c.garage")
    List<Car> findAllCars();

    @EntityGraph(value = "Car.garage", type = EntityGraph.EntityGraphType.LOAD)
    List<Car> findAll();
}
