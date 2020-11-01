package com.mtjb.examples.test_containers

import com.mtjb.examples.SqlSpec
import com.mtjb.examples.entities.CarGarage
import com.mtjb.examples.repositories.CarGarageRepository
import com.mtjb.examples.services.CarGarageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

import javax.persistence.EntityManager

@SpringBootTest
@ActiveProfiles("mssql-test")
class SqlServerTests extends SqlSpec {

    @Autowired CarGarageService carGarageService
    @Autowired CarGarageRepository carGarageRepository
    @Autowired EntityManager entityManager

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

    def "MSSQL-specific query syntax"() {
        given: "A new car garage saved"
            carGarageService.save(new CarGarage(name: "Foo"))
        when: "Querying the table using 'IIF'"
            def results = entityManager.createNativeQuery('''
                SELECT IIF(name = 'Foo', 'true', 'false')
                FROM dbo.car_garage
            ''').getResultList()
        then: "'true' returned"
            results == ['true']
        cleanup:
            carGarageRepository.deleteAll()
    }
}