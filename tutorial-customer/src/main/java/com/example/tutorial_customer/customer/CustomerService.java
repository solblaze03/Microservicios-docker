package com.example.tutorial_customer.customer;

import com.example.tutorial_customer.customer.model.Customer;
import com.example.tutorial_customer.customer.model.CustomerDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CustomerService {

    Customer get(Long id);

    List<Customer> findAll();

    ResponseEntity<?> save(Long id , CustomerDto client);

    void delete(Long id) throws Exception;

    Boolean existByName(String name);
}
