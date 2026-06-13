package com.medical.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.medical.dto.PurchaseReturnRequest;
import com.medical.entity.PurchaseReturn;
import com.medical.service.PurchaseReturnService;

@RestController
@RequestMapping("/api/purchase-returns")
@CrossOrigin("*")
public class PurchaseReturnController {

    @Autowired
    private PurchaseReturnService service;

    
    @PostMapping
    public PurchaseReturn save(
            @RequestBody PurchaseReturnRequest request){

        return service.save(request);
    }
    @GetMapping
    public List<PurchaseReturn> getAll(){

        return service.getAll();
    }
}