package com.medical.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medical.dto.SaleReturnItemRequest;
import com.medical.dto.SaleReturnRequest;
import com.medical.entity.Medicine;
import com.medical.entity.SaleReturn;
import com.medical.entity.SaleReturnItem;
import com.medical.repository.MedicineRepository;
import com.medical.repository.SaleReturnRepository;

@Service
public class SaleReturnService {

    @Autowired
    private SaleReturnRepository repository;

    @Autowired
    private MedicineRepository medicineRepository;
    

    public SaleReturn saveReturn(SaleReturnRequest request){

        SaleReturn saleReturn = new SaleReturn();

        saleReturn.setBillId(request.getBillId());
        saleReturn.setReturnDate(request.getReturnDate());


        for(SaleReturnItemRequest item : request.getItems()){


            Medicine medicine =
            medicineRepository.findById(item.getMedicineId())
            .orElseThrow();


            // Increase stock

            medicine.setQuantity(
                medicine.getQuantity()
                + item.getQuantity()
            );


            medicineRepository.save(medicine);



            SaleReturnItem returnItem =
            new SaleReturnItem();


            returnItem.setSaleReturn(saleReturn);

            returnItem.setMedicine(medicine);

            returnItem.setQuantity(
                item.getQuantity()
            );

            returnItem.setAmount(
                item.getAmount()
            );


            saleReturn.getItems().add(returnItem);


        }


        return repository.save(saleReturn);

    }
   

    public List<SaleReturn> getAll(){
        return repository.findAll();
    }
}