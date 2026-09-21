package com.medical.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.medical.dto.BillRequest;
import com.medical.entity.Bill;
import com.medical.entity.BillItem;
import com.medical.repository.BillItemRepository;
import com.medical.repository.BillRepository;
import com.medical.service.BillingService;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    private final BillingService billingService ;
    @Autowired
    private BillRepository billRepository;
    @Autowired
    BillItemRepository billItemRepository;
    public BillController(BillingService billingService) {
		super();
		this.billingService = billingService;
	}
    @GetMapping("/customer/{customerId}")
    public List<Bill> getBillsByCustomer(
            @PathVariable Long customerId){

        return billRepository.findByCustomer_Id(customerId);

    }
    @GetMapping("/search")
    public List<Bill> searchBills(

    @RequestParam(defaultValue="") String customer,

    @RequestParam(defaultValue="") String type

    ){

        return billRepository.searchBills(customer, type);

    }
    @GetMapping("/{id}")
    public Map<String, Object> getBill(@PathVariable Long id) {

        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bill Not Found"));

        List<BillItem> items = billItemRepository.findByBillId(id);

        Map<String, Object> result = new HashMap<>();
        result.put("bill", bill);
        result.put("items", items);

        return result;
    }
	@PostMapping
    public ResponseEntity<Bill> createBill(
            @RequestBody BillRequest request) {

        return ResponseEntity.ok(
                billingService.generateBill(request));
    }
	
	@GetMapping
	public List<Bill> getAllBills() {
	    return billingService.getAllBills();
	}
	
	@DeleteMapping("/{id}")
	public void deleteBill(@PathVariable Long id){
	    billRepository.deleteById(id);
	}

   

}