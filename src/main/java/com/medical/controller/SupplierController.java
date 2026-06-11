
package com.medical.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.medical.entity.Supplier;
import com.medical.service.SupplierService;

@RestController
@RequestMapping("/api/suppliers")
@CrossOrigin("*")
public class SupplierController {

    @Autowired
    private SupplierService service;

    @GetMapping
    public List<Supplier> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Supplier get(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping
    public Supplier save(@RequestBody Supplier supplier) {
        return service.save(supplier);
    }

    @PutMapping("/{id}")
    public Supplier update(
            @PathVariable Long id,
            @RequestBody Supplier supplier) {

        supplier.setId(id);

        return service.save(supplier);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}