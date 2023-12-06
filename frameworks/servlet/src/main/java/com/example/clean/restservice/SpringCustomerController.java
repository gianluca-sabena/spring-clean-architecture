package com.example.clean.restservice;

import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.clean.entities.Customer;


@RestController
public class SpringCustomerController {

    private CustomerService customerService;

    public SpringCustomerController(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @RequestMapping(value = "/customers", method = RequestMethod.POST)
    public Customer createCustomer(@RequestBody final Customer customer) {
        return customerService.create(customer);
    }

    @RequestMapping(value = "/customers/{customerId}", method = RequestMethod.GET)
    public Customer getCustomer(@PathVariable("customerId") final String customerId) {
        return customerService.getCustomerById(customerId);
    }

    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    public List<Customer> allCustomers() {
        return customerService.allCustomers();
    }
}
