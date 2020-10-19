package com.mtjb.examples

import com.mtjb.examples.entities.CarGarage
import com.mtjb.examples.repositories.CarGarageRepository
import com.mtjb.examples.services.CarGarageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class ExampleGroovyTests extends Specification {

    @Autowired CarGarageService carGarageService
    @Autowired CarGarageRepository carGarageRepository

    def "Basic entity save, fetch, and delete"() {
        given: "A new car garage saved"
            carGarageService.save(new CarGarage(name: "My First Car Garage"))
        when: "Fetched"
            List<CarGarage> saved = carGarageService.findAll()
        then: "The saved entity is returned"
            saved.name == ["My First Car Garage"]
        when: "Deleting"
            carGarageRepository.deleteAll()
        then: "All entities removed"
            carGarageRepository.findAll().isEmpty()
    }
}