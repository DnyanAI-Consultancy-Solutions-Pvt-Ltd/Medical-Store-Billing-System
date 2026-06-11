package com.medical.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medical.entity.Medicine;
import com.medical.entity.PurchaseReturn;
import com.medical.repository.MedicineRepository;
import com.medical.repository.PurchaseReturnRepository;

@Service
public class PurchaseReturnService {

    @Autowired
    private PurchaseReturnRepository repository;

    @Autowired
    private MedicineRepository medicineRepository;

    public PurchaseReturn save(
            PurchaseReturn pr){

        Medicine medicine =
            medicineRepository.findById(
            pr.getMedicine().getId())
            .orElseThrow();

        medicine.setQuantity(
            medicine.getQuantity()
            - pr.getQuantity());

        medicineRepository.save(medicine);

        pr.setReturnDate(LocalDate.now());

        return repository.save(pr);
    }
    public List<PurchaseReturn> getAll(){
        return repository.findAll();
    }
}