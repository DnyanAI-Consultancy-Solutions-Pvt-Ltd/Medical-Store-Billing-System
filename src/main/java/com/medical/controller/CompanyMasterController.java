package com.medical.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.medical.entity.CompanyMaster;
import com.medical.service.CompanyMasterService;

@RestController
@RequestMapping("/api/company")
@CrossOrigin("*")
public class CompanyMasterController {

    @Autowired
    private CompanyMasterService service;

    @PostMapping
    public CompanyMaster save(
            @RequestBody CompanyMaster company){

        return service.save(company);
    }

    @GetMapping
    public List<CompanyMaster> getAll(){

        return service.getAll();
    }

    @GetMapping("/{id}")
    public CompanyMaster get(
            @PathVariable Long id){

        return service.get(id);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Long id){

        service.delete(id);
    }
}