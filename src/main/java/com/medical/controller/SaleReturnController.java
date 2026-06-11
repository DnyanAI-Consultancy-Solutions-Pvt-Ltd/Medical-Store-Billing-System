package com.medical.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medical.entity.SaleReturn;
import com.medical.service.SaleReturnService;

@RestController
@RequestMapping("/api/sale-returns")
@CrossOrigin("*")
public class SaleReturnController {

    @Autowired
    private SaleReturnService service;

    @PostMapping
    public SaleReturn save(
            @RequestBody SaleReturn sr){

        return service.save(sr);
    }

    @GetMapping
    public List<SaleReturn> getAll(){
        return service.getAll();
    }
}