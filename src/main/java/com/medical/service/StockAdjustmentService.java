package com.medical.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medical.entity.Medicine;
import com.medical.entity.StockAdjustment;
import com.medical.repository.MedicineRepository;
import com.medical.repository.StockAdjustmentRepository;
@Service
public class StockAdjustmentService {
	 @Autowired
	    private StockAdjustmentRepository repository;

	    @Autowired
	    private MedicineRepository medicineRepository;


	public StockAdjustment save(
	        StockAdjustment sa){

	    Medicine medicine =
	        medicineRepository.findById(
	        sa.getMedicine().getId())
	        .orElseThrow();

	    medicine.setQuantity(
	        medicine.getQuantity()
	        - sa.getQuantity());

	    medicineRepository.save(medicine);

	    sa.setAdjustmentDate(LocalDate.now());

	    return repository.save(sa);
	}
	public List<StockAdjustment> getAll(){
        return repository.findAll();
    }
}
