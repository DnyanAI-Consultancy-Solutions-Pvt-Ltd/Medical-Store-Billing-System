package com.medical.controller;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medical.entity.Bill;
import com.medical.entity.Medicine;
import com.medical.entity.Purchase;
import com.medical.repository.BillItemRepository;
import com.medical.repository.BillRepository;
import com.medical.repository.CustomerRepository;
import com.medical.repository.MedicineRepository;
import com.medical.repository.PurchaseRepository;
import com.medical.repository.SupplierRepository;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin("*")
public class DashboardController {
	@Autowired private BillRepository billRepo; 
	@Autowired private BillItemRepository billItemRepo;
	@Autowired private PurchaseRepository purchaseRepo; 
	@Autowired private MedicineRepository medicineRepo; 
	@Autowired private SupplierRepository supplierRepository;
	@Autowired private CustomerRepository customerRepository;
	@GetMapping("/inventory") 
	public Map<String,Integer> inventoryGraph()
	{
		int available = 0; int lowStock = 0; 
		int outOfStock = 0; 
		List<Medicine> medicines = medicineRepo.findAll(); 
		for(Medicine m : medicines){ 
			if(m.getQuantity()==null || m.getQuantity()==0){ outOfStock++; 
			} else if(m.getQuantity()<=10){ lowStock++; } 
			else{ available++; } }
		Map<String,Integer> map = new HashMap<>();
		map.put("available", available); 
		map.put("lowStock", lowStock);
		map.put("outOfStock", outOfStock);
		return map; 
		}
	
	@GetMapping("/alerts")
	public Map<String, Object> getAlerts() {

	    Map<String, Object> map = new HashMap<>();

	    List<Medicine> lowStock =
	            medicineRepo.findByQuantityLessThan(10);

	    List<Medicine> expiring =
	            medicineRepo.findByExpiryDateBetween(
	                    LocalDate.now(),
	                    LocalDate.now().plusDays(30));

	    map.put("lowStock", lowStock);
	    map.put("expiring", expiring);

	    return map;
	}
	@GetMapping("/stast")
    public Map<String,Object> getDashboard() {

        Map<String,Object> dashboard =
                new HashMap<>();

        dashboard.put(
                "totalMedicines",
                medicineRepo.count());

        dashboard.put(
                "totalCustomers",
                customerRepository.count());

        

        dashboard.put(
                "outOfStock",
                medicineRepo
                        .findByQuantityLessThan(1)
                        .size());

       
        dashboard.put(
                "totalSuppliers",
                supplierRepository.count());

        LocalDate today = LocalDate.now();

        LocalDateTime start = today.atStartOfDay();

        LocalDateTime end = today.plusDays(1).atStartOfDay();

        dashboard.put(
            "totalBills",
            billRepo.countByBillDateBetween(start, end));
       

        	
        	// =========================
            // TODAY SALES
            // =========================

            double todaySales =
                    billRepo.findAll()
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
            		purchaseRepo.findAll()
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

            for(var item : billItemRepo.findAll()) {

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
            int i=(int)todayProfit;
            dashboard.put("todaySales", todaySales);

            dashboard.put("todayPurchase", todayPurchase);

            dashboard.put("todayProfit", i);

           
        	
        	

        return dashboard;
    }
	@GetMapping("/sales")
	public List<Double> salesGraph(){


	double[] months = new double[12];

	List<Bill> bills = billRepo.findAll();

	for(Bill b : bills){

	    if(b.getBillDate()!=null){

	        int month =
	        b.getBillDate().getMonthValue()-1;

	        months[month] +=
	        b.getTotalAmount();
	    }
	}

	List<Double> result = new ArrayList<>();

	for(double d : months){
	    result.add(d);
	}

	return result;
	
	}
	@GetMapping("/purchase")
	public List<Double> purchaseGraph(){

	double[] months = new double[12];

	List<Purchase> purchases =
	purchaseRepo.findAll();

	for(Purchase p : purchases){

	    if(p.getPurchaseDate()!=null){

	        int month =
	        p.getPurchaseDate().getMonthValue()-1;

	        months[month] +=
	        p.getTotalAmount();
	    }
	}

	List<Double> result = new ArrayList<>();

	for(double d : months){
	    result.add(d);
	}

	return result;


	}
	@GetMapping("/top-medicines")
	public Map<String,Object> topMedicines(){

	List<Medicine> medicines =
	medicineRepo.findAll();

	medicines.sort((a,b)->{

	    Integer qtyA =
	    billItemRepo.getSaleQty(a.getId());

	    Integer qtyB =
	    billItemRepo.getSaleQty(b.getId());

	    return qtyB.compareTo(qtyA);

	});

	List<String> names =
	new ArrayList<>();

	List<Integer> qtys =
	new ArrayList<>();

	for(int i=0;
	    i<Math.min(5, medicines.size());
	    i++){

	    Medicine m =
	    medicines.get(i);

	    names.add(
	    m.getMedicineName());
	    qtys.add(
	    	    billItemRepo.getSaleQty(
	    	    m.getId()));
	    	}

	    	Map<String,Object> map =
	    	new HashMap<>();

	    	map.put("labels", names);
	    	map.put("data", qtys);

	    	return map;

	    	}

}