package com.medical.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medical.dto.BillRequest;
import com.medical.entity.Bill;
import com.medical.service.BillingService;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    private final BillingService billingService ;

    public BillController(BillingService billingService) {
		super();
		this.billingService = billingService;
	}

	@PostMapping
    public ResponseEntity<Bill> createBill(
            @RequestBody BillRequest request) {

        return ResponseEntity.ok(
                billingService.generateBill(request));
    }
}