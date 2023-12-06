package com.example.clean.usecase.port;

import java.util.List;
import java.util.Optional;

import com.example.clean.entities.Customer;

public interface CustomerRepository {

    Customer create(Customer customer);

    Optional<Customer> findById(String id);

    Optional<Customer> findByEmail(String email);

    List<Customer> findAllCustomers();
}
