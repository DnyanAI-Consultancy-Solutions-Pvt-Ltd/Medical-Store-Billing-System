package com.medical.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medical.entity.Medicine;
import com.medical.entity.SaleReturn;
import com.medical.repository.MedicineRepository;
import com.medical.repository.SaleReturnRepository;

@Service
public class SaleReturnService {

    @Autowired
    private SaleReturnRepository repository;

    @Autowired
    private MedicineRepository medicineRepository;

    public SaleReturn save(SaleReturn sr){

        Medicine medicine =
            medicineRepository.findById(
            sr.getMedicine().getId())
            .orElseThrow();

        medicine.setQuantity(
            medicine.getQuantity()
            + sr.getQuantity());

        medicineRepository.save(medicine);

        sr.setReturnDate(LocalDate.now());

        return repository.save(sr);
    }

    public List<SaleReturn> getAll(){
        return repository.findAll();
    }
}