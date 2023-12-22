package com.example.clean.adapter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;

import com.example.clean.entities.Customer;

import org.junit.jupiter.api.Test;

import org.springframework.context.annotation.PropertySource;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

@SpringBootTest(classes = {DbCustomerRepository.class})
@PropertySource("classpath:jdbc.properties")
@Repository
class DbCustomerRepositoryTest {
    //@Autowired
    //private JdbcTemplate jdbcTemplate;
    @Autowired
    DbCustomerRepository dbCustomerRepository;

    @Test
    void createOne() {
        //DbCustomerRepository dbCustomerRepository = new DbCustomerRepository(jdbcTemplate);
        String id = UUID.randomUUID().toString();
        Customer testOne = dbCustomerRepository.create(Customer.builder().id(id).email("test-1@test-1").firstName("Test 1").lastName("Test 1").build());
        assertEquals(testOne.getId(), id);
    }
}