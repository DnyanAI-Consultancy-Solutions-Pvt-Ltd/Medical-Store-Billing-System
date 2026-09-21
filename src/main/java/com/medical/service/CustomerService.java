package com.medical.service;

import com.medical.entity.Customer;
import com.medical.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public Customer updateCustomer(Long id, Customer customer) {

        Customer existing = getCustomerById(id);

        existing.setCustomerName(customer.getCustomerName());
        existing.setMobileNo(customer.getMobileNo());

        return customerRepository.save(existing);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}