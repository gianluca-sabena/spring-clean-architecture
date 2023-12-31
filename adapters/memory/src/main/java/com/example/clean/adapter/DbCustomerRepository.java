package com.example.clean.adapter;

import com.example.clean.entities.Customer;
import com.example.clean.usecase.port.CustomerRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DbCustomerRepository implements CustomerRepository {
    Logger logger = LoggerFactory.getLogger(DbCustomerRepository.class);
    private final Map<String, Customer> inMemoryDb = new HashMap<>();

    public DbCustomerRepository() {
        logger.info("-------> DbCustomerRepository MEMORY");
    }

    @Override
    public Customer create(final Customer customer) {
        inMemoryDb.put(customer.getId(), customer);
        return customer;
    }
    @Override
    public Optional<Customer> findByEmail(final String email) {
        return inMemoryDb.values().stream()
                .filter(customer -> customer.getEmail().equals(email))
                .findAny();
    }
    @Override
    public Optional<Customer> findById(final String id) {
        return Optional.ofNullable(inMemoryDb.get(id));
    }

    @Override
    public List<Customer> findAllCustomers() {
        return new ArrayList<>(inMemoryDb.values());
    }
}
