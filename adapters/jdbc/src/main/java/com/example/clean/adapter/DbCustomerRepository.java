package com.example.clean.adapter;

import com.example.clean.entities.Customer;
import com.example.clean.usecase.port.CustomerRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

// TODO: Verify, Since we marged multiple modules, we need to have properties separated
//      This is probably a problem with the IoC of spring, if we use application.properties
//      h2 works, but postgresql doesn't
@PropertySource("classpath:jdbc.properties")
@Repository
// Required to autowire jdbctemplate
@EnableAutoConfiguration
public class DbCustomerRepository implements CustomerRepository {
    Logger logger = LoggerFactory.getLogger(DbCustomerRepository.class);

    @Autowired
    JdbcTemplate jdbcTemplate;


    @Autowired
    public DbCustomerRepository() {
        logger.info("-------> DbCustomerRepository JDBC");
    }

    @Override
    public Optional<Customer> findByEmail(final String email) {

            List<Customer> customer = jdbcTemplate.query(
                "SELECT id, email, lastname, firstname FROM CUSTOMER WHERE email = ?",
                (rs, rowNum) -> Customer.builder().id(rs.getString("id")).email(rs.getString("email"))
                            .lastName(rs.getString("lastname"))
                            .firstName(rs.getString("firstname")).build(),email);
            if (customer.isEmpty()) {
                return Optional.empty();
            } else {
                return Optional.of(customer.get(0));
            }

    }

    @Override
    public Customer create(final Customer customer) {
        logger.info("JDBC adapter - Create customer: {}", customer.getId());
        jdbcTemplate.update("INSERT INTO CUSTOMER(id, email, lastname, firstname) VALUES (?,?,?,?)", customer.getId(), customer.getEmail(), customer.getLastName(), customer.getFirstName());
        return customer;
    }

    @Override
    public Optional<Customer> findById(final String id) {
            List<Customer> customer = jdbcTemplate.query(
                "SELECT id, email, lastname, firstname FROM CUSTOMER WHERE id = ?",
                (rs, rowNum) -> Customer.builder().id(rs.getString("id")).email(rs.getString("email"))
                            .lastName(rs.getString("lastname"))
                            .firstName(rs.getString("firstname")).build(),id);
            if (customer.isEmpty()) {
                return Optional.empty();
            } else {
                return Optional.of(customer.get(0));
            }
    }

    @Override
    public List<Customer> findAllCustomers() {
        return jdbcTemplate.query(
                "SELECT id, email, lastname, firstname FROM CUSTOMER",
                (rs, rowNum) -> Customer.builder().id(rs.getString("id")).email(rs.getString("email"))
                        .lastName(rs.getString("lastname"))
                        .firstName(rs.getString("firstname")).build());
    }
}
