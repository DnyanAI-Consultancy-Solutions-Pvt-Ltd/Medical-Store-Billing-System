package com.medical.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medical.dto.SaleReturnRequest;
import com.medical.entity.Bill;
import com.medical.entity.Customer;
import com.medical.entity.SaleReturn;
import com.medical.repository.BillRepository;
import com.medical.repository.CustomerRepository;
import com.medical.service.SaleReturnService;

@RestController
@RequestMapping("/api/sale-returns")
@CrossOrigin("*")
public class SaleReturnController {

    @Autowired
    private SaleReturnService service;
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public List<SaleReturn> getAll(){
        return service.getAll();
    }
    @GetMapping("/customers")
    public List<Customer> customers(){
        return customerRepository.findAll();
    }
    @GetMapping("/customer/{customerId}")
    public List<Bill> bills(@PathVariable Long customerId){

        return billRepository.findByCustomer_Id(customerId);

    }
    @PostMapping
    public SaleReturn save(@RequestBody SaleReturnRequest request){

        return service.saveReturn(request);

    }
  
}