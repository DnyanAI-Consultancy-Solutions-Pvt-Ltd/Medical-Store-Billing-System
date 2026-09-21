package com.medical.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medical.dto.OpeningStockRequest;
import com.medical.entity.Medicine;
import com.medical.entity.OpeningStock;
import com.medical.repository.MedicineRepository;
import com.medical.repository.OpeningStockRepository;

@Service
public class OpeningStockService {

    @Autowired
    private OpeningStockRepository openingStockRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    public OpeningStock save(OpeningStockRequest request){

        Medicine medicine =
                medicineRepository.findById(request.getMedicineId())
                .orElseThrow();

        // Stock Update
        medicine.setQuantity(
                medicine.getQuantity() + request.getQuantity()
        );

        medicineRepository.save(medicine);

        // History Save
        OpeningStock stock = new OpeningStock();

        stock.setMedicine(medicine);

        stock.setQuantity(request.getQuantity());

        stock.setOpeningDate(request.getOpeningDate());

        return openingStockRepository.save(stock);
    }

}