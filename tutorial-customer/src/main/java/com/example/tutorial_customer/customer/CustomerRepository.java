package com.example.tutorial_customer.customer;


import com.example.tutorial_customer.customer.model.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findAllByOrderByIdAsc();

    Boolean existsByName(String name);
}
