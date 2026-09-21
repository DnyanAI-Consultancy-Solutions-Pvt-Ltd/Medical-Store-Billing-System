package com.medical.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medical.entity.CompanyMaster;
import com.medical.repository.CompanyMasterRepository;

@Service
public class CompanyMasterService {

    @Autowired
    private CompanyMasterRepository repository;

    public CompanyMaster save(
            CompanyMaster company){

        return repository.save(company);
    }

    public List<CompanyMaster> getAll(){

        return repository.findAll();
    }

    public CompanyMaster get(Long id){

        return repository.findById(id)
                .orElse(null);
    }

    public void delete(Long id){

        repository.deleteById(id);
    }
}