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

import com.medical.dto.PaymentRequest;
import com.medical.entity.Payment;
import com.medical.entity.Supplier;
import com.medical.repository.PaymentRepository;
import com.medical.repository.SupplierRepository;
import com.medical.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin("*")
public class PaymentController {
	 @Autowired
	    private PaymentService service;


    @Autowired
    private PaymentRepository repository;
    @Autowired
    private PaymentService paymentService;
   
    @PostMapping
    public Payment save(@RequestBody PaymentRequest request) {
    	System.out.println("PaymentController Called");
        return paymentService.save(request);
    }
    @GetMapping("/supplier/{supplierId}")
    public List<Payment> getPaymentHistory(
            @PathVariable Long supplierId){

        return paymentService.getBySupplier(supplierId);

    }
    @GetMapping("/history/{supplierId}")
    public List<Payment> getHistory(@PathVariable Long supplierId){
        return repository.findBySupplierId(supplierId);
    }
    @GetMapping
    public List<Payment> getAll(){
        return repository.findAll();
    }
    
    @GetMapping("/outstanding/{supplierId}")
    public Double outstanding(
            @PathVariable Long supplierId){

        return service.getOutstanding(supplierId);
    }
}