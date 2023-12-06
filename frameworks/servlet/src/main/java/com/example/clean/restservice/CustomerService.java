package com.example.clean.restservice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.clean.adapter.DbCustomerRepository;
import com.example.clean.entities.Customer;
import com.example.clean.usecase.CustomerUseCase;

@Service
public class CustomerService {
    private CustomerUseCase customerUseCase;

    public CustomerService(DbCustomerRepository dbCustomerRepository) {
        this.customerUseCase = new CustomerUseCase(dbCustomerRepository);
    }
    public Customer create(Customer customer){
        return customerUseCase.create(customer);

    }
    public Customer getCustomerById(String customerId){
        return customerUseCase.findById(customerId).orElseThrow(() -> new RuntimeException("customer not found"));
    }

    public List<Customer> allCustomers() {
        return new ArrayList<>(customerUseCase.findAllCustomers());
    }
}
