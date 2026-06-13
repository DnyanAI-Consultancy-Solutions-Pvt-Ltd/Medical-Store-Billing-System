package com.medical.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.medical.dto.MovementReportDTO;
import com.medical.entity.Medicine;
import com.medical.repository.*;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin("*")
public class ReportController {

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private PurchaseItemRepository purchaseItemRepository;

    @Autowired
    private BillItemRepository billItemRepository;

    @Autowired
    private PurchaseReturnRepository purchaseReturnRepository;

    @Autowired
    private SaleReturnRepository saleReturnRepository;

    @GetMapping("/movement")
    public List<MovementReportDTO> movementReport(){

        List<MovementReportDTO> list =
                new ArrayList<>();

        List<Medicine> medicines =
                medicineRepository.findAll();

        for(Medicine m : medicines){

            MovementReportDTO dto =
                    new MovementReportDTO();

            dto.setMedicineName(
                    m.getMedicineName());

            dto.setOpeningStock(
                    m.getOpeningStock());

            dto.setPurchaseQty(
                    purchaseItemRepository
                    .getPurchaseQty(m.getId()));

            dto.setSaleQty(
                    billItemRepository
                    .getSaleQty(m.getId()));

            dto.setPurchaseReturnQty(
                    purchaseReturnRepository
                    .getReturnQty(m.getId()));

            dto.setSaleReturnQty(
                    saleReturnRepository
                    .getReturnQty(m.getId()));

            dto.setClosingStock(
                    m.getQuantity());

            list.add(dto);
        }

        return list;
    }
}