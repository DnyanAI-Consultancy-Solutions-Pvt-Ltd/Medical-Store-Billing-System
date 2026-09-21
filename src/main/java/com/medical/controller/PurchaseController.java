package com.medical.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.medical.dto.PurchaseRequest;
import com.medical.entity.Purchase;
import com.medical.service.PurchaseService;

@RestController
@RequestMapping("/api/purchases")
@CrossOrigin("*")
public class PurchaseController {

    @Autowired
    private PurchaseService service;
    @PostMapping
    public Purchase createPurchase(
            @RequestBody PurchaseRequest request) {

        return service
                .createPurchase(request);
    }
    @GetMapping
    public List<Purchase> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Purchase get(@PathVariable Long id) {
        return service.get(id);
    }

   /* @PostMapping
    public Purchase save(@RequestBody Purchase purchase) {
        return service.save(purchase);
    }*/

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}