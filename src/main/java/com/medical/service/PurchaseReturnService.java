package com.medical.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medical.dto.PurchaseReturnRequest;
import com.medical.entity.Medicine;
import com.medical.entity.PurchaseReturn;
import com.medical.entity.Supplier;
import com.medical.repository.MedicineRepository;
import com.medical.repository.PurchaseReturnRepository;
import com.medical.repository.SupplierRepository;

@Service
public class PurchaseReturnService {

    @Autowired
    private PurchaseReturnRepository repository;

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    public PurchaseReturn save(
            PurchaseReturnRequest request){

        Supplier supplier =
                supplierRepository
                .findById(request.getSupplierId())
                .orElseThrow();

        Medicine medicine =
                medicineRepository
                .findById(request.getMedicineId())
                .orElseThrow();

        if(medicine.getQuantity() <
                request.getQuantity()){

            throw new RuntimeException(
                    "Not enough stock");
        }

        medicine.setQuantity(
                medicine.getQuantity()
                - request.getQuantity());

        medicineRepository.save(medicine);

        PurchaseReturn pr =
                new PurchaseReturn();

        pr.setSupplier(supplier);
        pr.setMedicine(medicine);
        pr.setQuantity(request.getQuantity());

        pr.setAmount(
                request.getAmount());

        pr.setReturnDate(
                LocalDate.now());

        return repository.save(pr);
    }

    public List<PurchaseReturn> getAll(){
        return repository.findAll();
    }
}