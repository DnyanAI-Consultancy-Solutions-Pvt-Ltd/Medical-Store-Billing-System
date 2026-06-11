package com.medical.controller;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medical.repository.BillItemRepository;
import com.medical.repository.BillRepository;
import com.medical.repository.CustomerRepository;
import com.medical.repository.MedicineRepository;
import com.medical.repository.PurchaseRepository;
import com.medical.repository.SupplierRepository;

@RestController
public class DashboardController {

    private final MedicineRepository medicineRepository;
    private final CustomerRepository customerRepository;
    private final SupplierRepository supplierRepository;
    private final BillRepository billRepository;
    private final PurchaseRepository purchaseRepository;
    private final BillItemRepository billItemRepository;
    

	public DashboardController(MedicineRepository medicineRepository, CustomerRepository customerRepository,
			SupplierRepository supplierRepository, BillRepository billRepository, PurchaseRepository purchaseRepository,
			BillItemRepository billItemRepository) {
		super();
		this.medicineRepository = medicineRepository;
		this.customerRepository = customerRepository;
		this.supplierRepository = supplierRepository;
		this.billRepository = billRepository;
		this.purchaseRepository = purchaseRepository;
		this.billItemRepository = billItemRepository;
	}


	@GetMapping("/api/dashboard")
    public Map<String,Object> getDashboard() {

        Map<String,Object> dashboard =
                new HashMap<>();

        dashboard.put(
                "totalMedicines",
                medicineRepository.count());

        dashboard.put(
                "totalCustomers",
                customerRepository.count());

        dashboard.put(
                "lowStock",
                medicineRepository
                        .findByQuantityLessThan(10)
                        .size());

        dashboard.put(
                "outOfStock",
                medicineRepository
                        .findByQuantityLessThan(1)
                        .size());

        dashboard.put(
                "expiringSoon",
                medicineRepository
                        .findByExpiryDateBetween(
                                LocalDate.now(),
                                LocalDate.now().plusDays(30))
                        .size());
        dashboard.put(
                "totalSuppliers",
                supplierRepository.count());

        dashboard.put(
                "totalBills",
                billRepository.count());
       

        	dashboard.put(
        	    "totalPurchases",
        	    purchaseRepository.count());
        	// =========================
            // TODAY SALES
            // =========================

            double todaySales =
                    billRepository.findAll()
                            .stream()
                            .filter(b ->
                                    b.getBillDate() != null &&
                                    b.getBillDate()
                                     .toLocalDate()
                                     .equals(LocalDate.now()))
                            .mapToDouble(b ->
                                    b.getTotalAmount() == null
                                            ? 0
                                            : b.getTotalAmount())
                            .sum();

            // =========================
            // TODAY PURCHASE
            // =========================

            double todayPurchase =
                    purchaseRepository.findAll()
                            .stream()
                            .filter(p ->
                                    p.getPurchaseDate() != null &&
                                    p.getPurchaseDate()
                                     .equals(LocalDate.now()))
                            .mapToDouble(p ->
                                    p.getTotalAmount() == null
                                            ? 0
                                            : p.getTotalAmount())
                            .sum();

            // =========================
            // TODAY PROFIT
            // =========================

            double todayCost = 0;

            for(var item : billItemRepository.findAll()) {

                if(item.getBill() != null
                   && item.getBill().getBillDate() != null
                   && item.getBill()
                          .getBillDate()
                          .toLocalDate()
                          .equals(LocalDate.now())) {

                    Double purchasePrice =
                        item.getMedicine().getPurchasePrice();

                    if(purchasePrice == null) {
                        purchasePrice = 0.0;
                    }

                    todayCost +=
                        purchasePrice *
                        item.getQuantity();
                }
            }

            double todayProfit =
                    todaySales - todayCost;

            dashboard.put("todaySales", todaySales);

            dashboard.put("todayPurchase", todayPurchase);

            dashboard.put("todayProfit", todayProfit);

           
        	
        	

        return dashboard;
    }
}