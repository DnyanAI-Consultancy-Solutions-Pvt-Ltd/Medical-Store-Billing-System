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

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
private PurchaseRepository purchaseRepository;

    public Payment save(PaymentRequest request) {

        Supplier supplier =
                supplierRepository
                .findById(request.getSupplierId())
                .orElseThrow(() ->
                        new RuntimeException("Supplier Not Found"));

        Payment payment = new Payment();

        payment.setSupplier(supplier);

        payment.setAmount(request.getAmount());

        payment.setPaymentMode(
                request.getPaymentMode());

        payment.setRemarks(
                request.getRemarks());

        if(request.getPaymentDate() == null) {
            payment.setPaymentDate(LocalDate.now());
        } else {
            payment.setPaymentDate(
                    request.getPaymentDate());
        }

        return paymentRepository.save(payment);
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

    public Double getOutstanding(Long supplierId){

        double totalPurchase =
                purchaseRepository.findAll()
                .stream()
                .filter(p ->
                        p.getSupplier()!=null &&
                        p.getSupplier().getId().equals(supplierId))
                .mapToDouble(Purchase::getTotalAmount)
                .sum();

        double totalPayment =
                paymentRepository.findBySupplierId(supplierId)
                .stream()
                .mapToDouble(Payment::getAmount)
                .sum();

        return totalPurchase - totalPayment;
    }
}