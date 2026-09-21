package com.medical.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.medical.entity.Medicine;
import com.medical.entity.StockAdjustment;
import com.medical.repository.MedicineRepository;
import com.medical.service.StockAdjustmentService;

@RestController
@RequestMapping("/api/stock-adjustments")
@CrossOrigin("*")
public class StockAdjustmentController {

    @Autowired
    private StockAdjustmentService service;

    @Autowired
    private MedicineRepository medicineRepository;


    @PostMapping
    public StockAdjustment save(
            @RequestBody StockAdjustmentRequest request) {

        Medicine medicine =
                medicineRepository.findById(request.getMedicineId())
                .orElseThrow(() ->
                    new RuntimeException("Medicine not found")
                );

        StockAdjustment sa = new StockAdjustment();

        sa.setMedicine(medicine);
        sa.setQuantity(request.getQuantity());
        sa.setReason(request.getReason());

        return service.save(sa);
    }


    @GetMapping
    public List<StockAdjustment> getAll() {

        return service.getAll();
    }


    public static class StockAdjustmentRequest {

        private Long medicineId;
        private Integer quantity;
        private String reason;


        public Long getMedicineId() {
            return medicineId;
        }

        public void setMedicineId(Long medicineId) {
            this.medicineId = medicineId;
        }


        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }


        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }
}