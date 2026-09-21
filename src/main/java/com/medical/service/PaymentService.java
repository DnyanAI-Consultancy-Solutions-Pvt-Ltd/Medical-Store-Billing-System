package com.medical.service;


import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medical.dto.PaymentRequest;
import com.medical.entity.Payment;
import com.medical.entity.Purchase;
import com.medical.entity.Supplier;
import com.medical.repository.PaymentRepository;
import com.medical.repository.PurchaseRepository;
import com.medical.repository.SupplierRepository;

import jakarta.transaction.Transactional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
private PurchaseRepository purchaseRepository;

    @Transactional
    public Payment save(PaymentRequest request) {

        Supplier supplier =
                supplierRepository
                .findById(request.getSupplierId())
                .orElseThrow(() ->
                        new RuntimeException("Supplier Not Found"));

        Payment payment = new Payment();

        payment.setSupplier(supplier);
        payment.setAmount(request.getAmount());
        payment.setPaymentMode(request.getPaymentMode());
        payment.setRemarks(request.getRemarks());

        if(request.getPaymentDate() == null) {
            payment.setPaymentDate(LocalDate.now());
        } else {
            payment.setPaymentDate(request.getPaymentDate());
        }

        // Payment Save
        System.out.println("PaymentService Called");
        Payment savedPayment = paymentRepository.save(payment);
        System.out.println("Old = " + supplier.getBalance());

        // Update Supplier Balance
        supplier.setBalance(
        	    (supplier.getBalance() == null ? 0.0 : supplier.getBalance())
        	    - request.getAmount()
        	);
        System.out.println("New = " + supplier.getBalance());
        	supplierRepository.saveAndFlush(supplier);

        	System.out.println("Supplier Saved");

        return savedPayment;
    }
    public List<Payment> getAll() {
        return paymentRepository.findAll();
    }

    public Payment get(Long id) {
        return paymentRepository
                .findById(id)
                .orElse(null);
    }

    public void delete(Long id) {
        paymentRepository.deleteById(id);
    }
 // Outstanding Balance

    public Double getOutstanding(Long supplierId) {

        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow();

        return supplier.getBalance() == null ? 0.0 : supplier.getBalance();
    }
    public List<Payment> getBySupplier(Long supplierId){

        return paymentRepository.findBySupplierId(supplierId);
    }
    public List<Payment> getHistory(Long supplierId){
        return paymentRepository.findBySupplierId(supplierId);
    }
}