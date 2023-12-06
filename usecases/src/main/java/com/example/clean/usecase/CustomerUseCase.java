package com.example.clean.usecase;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.clean.entities.Customer;
import com.example.clean.usecase.exception.CustomerAlreadyExistsException;
import com.example.clean.usecase.exception.CustomerValidationException;
import com.example.clean.usecase.port.CustomerRepository;
import static org.apache.commons.lang3.StringUtils.isBlank;

public final class CustomerUseCase {

    private final CustomerRepository repository;

    public CustomerUseCase(final CustomerRepository repository) {
        this.repository = repository;
    }

    public Customer create(final Customer customer) {
        this.validateCreateCustomer(customer);
        if (repository.findByEmail(customer.getEmail()).isPresent()) {
            throw new CustomerAlreadyExistsException(customer.getEmail());
        }
        var customerToSave = Customer.builder()
            .id(UUID.randomUUID().toString())
            .email(customer.getEmail())
            .lastName(customer.getLastName())
            .firstName(customer.getFirstName())
            .build();
        return repository.create(customerToSave);
    }

    public Optional<Customer> findById(final String id) {
        return repository.findById(id);
    }

    public List<Customer> findAllCustomers() {
        return repository.findAllCustomers();
    }

    private void validateCreateCustomer(final Customer customer) {
        if (customer == null) throw new CustomerValidationException("Customer should not be null");
        if (isBlank(customer.getEmail())) throw new CustomerValidationException("Email should not be null");
        if (isBlank(customer.getFirstName())) throw new CustomerValidationException("First name should not be null");
        if (isBlank(customer.getLastName())) throw new CustomerValidationException("Last name should not be null");
    }
}
