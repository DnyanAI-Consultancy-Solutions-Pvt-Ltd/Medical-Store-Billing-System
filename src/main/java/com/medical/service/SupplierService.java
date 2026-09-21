package com.medical.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medical.entity.Supplier;
import com.medical.repository.SupplierRepository;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository repository;

    public List<Supplier> getAll() {
        return repository.findAll();
    }

    public Supplier save(Supplier supplier) {
        return repository.save(supplier);
    }

    public Supplier get(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}