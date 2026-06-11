package com.medical.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.medical.entity.StockAdjustment;
import com.medical.service.StockAdjustmentService;

@RestController
@RequestMapping("/api/stock-adjustments")
@CrossOrigin("*")
public class StockAdjustmentController {

    @Autowired
    private StockAdjustmentService service;

    @PostMapping
    public StockAdjustment save(
            @RequestBody StockAdjustment sa){

        return service.save(sa);
    }

    @GetMapping
    public List<StockAdjustment> getAll(){

        return service.getAll();
    }
}