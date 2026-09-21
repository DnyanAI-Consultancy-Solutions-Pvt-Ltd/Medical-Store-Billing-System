package com.medical.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medical.dto.OpeningStockRequest;
import com.medical.entity.OpeningStock;
import com.medical.repository.OpeningStockRepository;
import com.medical.service.OpeningStockService;

@RestController
@RequestMapping("/api/opening-stock")
@CrossOrigin("*")
public class OpeningStockController {

    @Autowired
    private OpeningStockService service;

    @Autowired
    private OpeningStockRepository repository;

    @PostMapping
    public OpeningStock save(
            @RequestBody OpeningStockRequest request){

        return service.save(request);
    }

    @GetMapping
    public List<OpeningStock> getAll(){

        return repository.findAll();
    }

}