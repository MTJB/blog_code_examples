package com.mtjb.examples.criteria_queries

import com.mtjb.examples.dto.CarDto
import com.mtjb.examples.dto.CarGarageDto
import com.mtjb.examples.entities.Car
import com.mtjb.examples.entities.CarGarage
import com.mtjb.examples.repositories.CarRepository
import com.mtjb.examples.services.CarGarageService
import com.mtjb.examples.services.CarService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@SpringBootTest
@ContextConfiguration
class CriteriaQueryTests extends Specification {

    @Autowired CarGarageService carGarageService
    @Autowired CarService carService
    @Autowired CarRepository carRepository

    def cleanup() {
        carRepository.deleteAll()
    }

    def "findCarsByMake returns Cars filtered by make for a specified garage"() {
        given: "2 Car garages, with 3 Cars"
            CarGarage g1 = carGarageService.create(new CarGarageDto(name: "Browns"))
            CarGarage g2 = carGarageService.create(new CarGarageDto(name: "Smyths"))
            ["Ford", "Volkswagen", "BMW"].each {
                carService.create(new CarDto(garage: g1, make: it))
                carService.create(new CarDto(garage: g2, make: it))
            }
        when: "Fetching 'Volkswagen's for g1"
            List<Car> cars = carGarageService.findCarsByMake(new CarGarageDto(id: g1.id, name: g1.name), "Volkswagen")
        then: "1 car returned"
            cars.collect { [it.garage.id, it.make] } == [[g1.id, "Volkswagen"]]
    }


    def "findAll"() {
        given: "2 Car garages, with 3 Cars"
            CarGarage g1 = carGarageService.create(new CarGarageDto(name: "Browns"))
            CarGarage g2 = carGarageService.create(new CarGarageDto(name: "Smyths"))
            carService.create(new CarDto(garage: g1, make: "Ford"))
            carService.create(new CarDto(garage: g2, make: "BMW"))
        when: "Fetching all Cars"
            def cars = carService.findAll()
        then: "Both returned"
            cars.size() == 2
    }

    def "findAllCarsByEntityGraph"() {
        given: "2 Car garages, with 3 Cars"
            CarGarage g1 = carGarageService.create(new CarGarageDto(name: "Browns"))
            CarGarage g2 = carGarageService.create(new CarGarageDto(name: "Smyths"))
            carService.create(new CarDto(garage: g1, make: "Ford"))
            carService.create(new CarDto(garage: g2, make: "BMW"))
        when: "Fetching all Cars"
            def cars = carService.findAllCarsByEntityGraph()
        then: "Both returned"
            cars.size() == 2
    }
}