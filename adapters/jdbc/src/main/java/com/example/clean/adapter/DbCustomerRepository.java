package com.example.clean.adapter;

import com.example.clean.entities.Customer;
import com.example.clean.usecase.port.CustomerRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class DbCustomerRepository implements CustomerRepository {
    Logger logger = LoggerFactory.getLogger(DbCustomerRepository.class);

    private JdbcTemplate jdbcTemplate;

    private final Map<String, Customer> inMemoryDb = new HashMap<>();

    public DbCustomerRepository(JdbcTemplate jdbcTemplate) {
        logger.info("-------> DbCustomerRepository JDBC - {}", jdbcTemplate.toString());
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Customer> findByEmail(final String email) {
        return inMemoryDb.values().stream()
                .filter(customer -> customer.getEmail().equals(email))
                .findAny();
    }

    @Override
    public Customer create(final Customer customer) {
        logger.info("JDBC adapter - Create customer: {}", customer.getId());
        jdbcTemplate.update("INSERT INTO customer(id, email, lastName, firstName) VALUES (?,?,?,?)", customer.getId(), customer.getEmail(), customer.getLastName(), customer.getFirstName());
        //inMemoryDb.put(customer.getId(), customer);
        return customer;
    }

    @Override
    public Optional<Customer> findById(final String id) {
        return Optional.ofNullable(inMemoryDb.get(id));
    }

    @Override
    public List<Customer> findAllCustomers() {
        return jdbcTemplate.query(
                "SELECT id, email, lastName, firstName FROM customer",
                (rs, rowNum) -> Customer.builder().id(rs.getString("id")).email(rs.getString("email"))
                        .lastName(rs.getString("lastName"))
                        .firstName(rs.getString("firstName")).build());
    }
}
