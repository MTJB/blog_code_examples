package com.mtjb.examples.criteria_queries

import com.mtjb.examples.dto.CarDto
import com.mtjb.examples.dto.CarGarageDto
import com.mtjb.examples.entities.Car
import com.mtjb.examples.entities.CarGarage
import com.mtjb.examples.repositories.CarRepository
import com.mtjb.examples.services.CarGarageService
import com.mtjb.examples.services.CarService
import org.hibernate.Session
import org.hibernate.stat.Statistics
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import javax.persistence.EntityManager

@SpringBootTest
@ContextConfiguration
class NPlusOneTests extends Specification {

    @Autowired CarGarageService carGarageService
    @Autowired CarService carService
    @Autowired CarRepository carRepository
    @Autowired EntityManager entityManager

    def cleanup() {
        carRepository.deleteAll()
    }

    def "Car.garage entity graph executes one query to load all relationships"(String graphType) {
        setup: "2 Car garages, with 3 Cars"
            CarGarage g1 = carGarageService.create(new CarGarageDto(name: "Browns"))
            CarGarage g2 = carGarageService.create(new CarGarageDto(name: "Smyths"))
            carService.create(new CarDto(garage: g1, make: "Ford"))
            carService.create(new CarDto(garage: g2, make: "BMW"))
        and: "Clear statistics"
            Session session = entityManager.unwrap(Session.class)
            Statistics statistics = session.getSessionFactory().getStatistics()
            statistics.setStatisticsEnabled(true)
            statistics.clear()
            assert statistics.getQueryExecutionCount() == 0
        when: "Fetching all Cars using the Car.garage entity graph"
            List<Car> cars = entityManager.createQuery("SELECT c FROM Car c")
                    .setHint(graphType, entityManager.getEntityGraph("Car.garage"))
                    .getResultList() as List<Car>
        then: "Only 1 query executed"
            statistics.getQueryExecutionCount() == 1
            statistics.getPrepareStatementCount() == 1
        and: "Both cars returned"
            cars.size() == 2
        cleanup:
            statistics.setStatisticsEnabled(false)
        where:
            graphType << ["javax.persistence.loadgraph", "javax.persistence.fetchgraph"]
    }
}