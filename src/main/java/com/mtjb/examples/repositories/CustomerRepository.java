package com.mtjb.examples.repositories;

import com.mtjb.examples.entities.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Set;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    @Query("FROM Customer WHERE id IN :ids")
    Set<Customer> findAllById(@Param("ids") Collection<Long> ids);
}
