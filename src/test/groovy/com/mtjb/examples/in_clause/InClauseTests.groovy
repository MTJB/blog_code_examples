package com.mtjb.examples.in_clause

import com.mtjb.examples.test_profiles.SqlSpec
import com.mtjb.examples.entities.Customer
import com.mtjb.examples.repositories.CustomerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.InvalidDataAccessResourceUsageException
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils
import spock.lang.Shared

@SpringBootTest
@ActiveProfiles("mssql-test")
class InClauseTests extends SqlSpec {

    @Autowired CustomerRepository customerRepository

    @Shared boolean setupDone = false

    def setup() {
        if (!setupDone) {
            10.times { customerRepository.save(new Customer(RandomStringUtils.randomAlphabetic(2), RandomStringUtils.randomAlphabetic(2))) }
            setupDone = true
        }
    }

    def "Find all by id"(Set<Long> ids) {
        expect: "Size to be equal"
            customerRepository.findAllById(ids).size() == ids.size()
        where:
            ids                                 | _
            [1L, 2L]                            | _
            [2L, 3L]                            | _
            [3L, 4L, 5L]                        | _
            [1L, 2L, 3L, 4L, 5L]                | _
            [4L, 5L, 6L, 7L, 8L, 9L]            | _
            [4L, 5L, 6L, 7L, 8L, 9L, 10L]       | _
            [3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L]   | _
    }

    def "Param size limit"() {
        when: "Calling the query with 2025 params"
            customerRepository.findAllById(1L..2000 as Set)
        then:
            noExceptionThrown()
        when: "Calling the query with 2099 params - this is rounded up to next power of 2!"
            customerRepository.findAllById(1L..2099 as Set)
        then:
            thrown(InvalidDataAccessResourceUsageException)
    }
}