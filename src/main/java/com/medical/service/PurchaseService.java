package com.medical.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medical.dto.PurchaseItemRequest;
import com.medical.dto.PurchaseRequest;
import com.medical.entity.Medicine;
import com.medical.entity.Purchase;
import com.medical.entity.PurchaseItem;
import com.medical.entity.Supplier;
import com.medical.repository.MedicineRepository;
import com.medical.repository.PurchaseItemRepository;
import com.medical.repository.PurchaseRepository;
import com.medical.repository.SupplierRepository;

import jakarta.transaction.Transactional;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
  private SupplierRepository supplierRepository;
    @Autowired
  private MedicineRepository medicineRepository;
    @Autowired
  private PurchaseItemRepository purchaseItemRepository;
    public List<Purchase> getAll() {
        return purchaseRepository.findAll();
    }
    @Transactional
    public Purchase createPurchase(PurchaseRequest request) {

        Supplier supplier =
            supplierRepository.findById(
                request.getSupplierId())
                .orElseThrow();

        Purchase purchase = new Purchase();

        purchase.setSupplier(supplier);
        purchase.setInvoiceNo(request.getInvoiceNo());

        purchase.setPurchaseDate(LocalDate.now());

        purchase = purchaseRepository.save(purchase);

        double total = 0;

        for(PurchaseItemRequest itemRequest :
                request.getItems()) {

            Medicine medicine =
                medicineRepository.findById(
                    itemRequest.getMedicineId())
                    .orElseThrow();

            PurchaseItem item =
                new PurchaseItem();

            item.setPurchase(purchase);

            item.setMedicine(medicine);

            item.setQuantity(
                itemRequest.getQuantity());

            item.setPurchaseRate(
                itemRequest.getPurchaseRate());

            item.setSaleRate(
                itemRequest.getSaleRate());

            double amount =
                itemRequest.getQuantity()
                * itemRequest.getPurchaseRate();

            item.setAmount(amount);

            purchaseItemRepository.save(item);

            total += amount;

            // STOCK UPDATE

            medicine.setPurchasePrice(
            	    itemRequest.getPurchaseRate());

            	medicine.setMrp(
            	    itemRequest.getSaleRate());

            	medicine.setQuantity(
            	    medicine.getQuantity()
            	    + itemRequest.getQuantity());

            	medicineRepository.save(medicine);
        }

        purchase.setTotalAmount(total);

        return purchaseRepository.save(purchase);
    }
    public Purchase save(Purchase purchase) {
        return purchaseRepository.save(purchase);
    }

    public Purchase get(Long id) {
        return purchaseRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        purchaseRepository.deleteById(id);
    }
}