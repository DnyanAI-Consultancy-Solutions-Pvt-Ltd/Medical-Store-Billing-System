package com.medical.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.medical.dto.BillMedicineDto;
import com.medical.dto.BillRequest;
import com.medical.entity.Bill;
import com.medical.entity.BillItem;
import com.medical.entity.Customer;
import com.medical.entity.Medicine;
import com.medical.repository.BillItemRepository;
import com.medical.repository.BillRepository;
import com.medical.repository.CustomerRepository;
import com.medical.repository.MedicineRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BillingService {

    private final CustomerRepository customerRepository;
    private final MedicineRepository medicineRepository;
    private final BillRepository billRepository;
    private final BillItemRepository billItemRepository;

    public BillingService(
            CustomerRepository customerRepository,
            MedicineRepository medicineRepository,
            BillRepository billRepository,
            BillItemRepository billItemRepository) {

        this.customerRepository = customerRepository;
        this.medicineRepository = medicineRepository;
        this.billRepository = billRepository;
        this.billItemRepository = billItemRepository;
    }

    public Bill generateBill(BillRequest request) {
    	Customer customer;

    	if(request.getCustomerId() != null){

    	    customer = customerRepository
    	            .findById(request.getCustomerId())
    	            .orElseThrow();

    	}else{

    	    customer = customerRepository
    	            .findByCustomerName(request.getCustomerName());

    	    if(customer == null){

    	        customer = new Customer();

    	        customer.setCustomerName(request.getCustomerName());
    	        customer.setMobileNo("");
    	        customer.setAddress("");
    	        customer.setBalance(0.0);

    	        customer = customerRepository.save(customer);
    	    }
    	}
         
        double subTotal = 0;

        Bill bill = new Bill();

        bill.setCustomer(customer);
        bill.setBillDate(LocalDateTime.now());

        bill.setDoctorName(
                request.getDoctorName());

        bill.setBillType(
                request.getBillType());

        Bill savedBill =
                billRepository.save(bill);

        for (BillMedicineDto dto :
                request.getMedicines()) {

            Medicine medicine =
                    medicineRepository.findById(
                            dto.getMedicineId())
                            .orElseThrow();

            if (medicine.getQuantity()
                    < dto.getQuantity()) {

                throw new RuntimeException(
                        "Stock not available for "
                                + medicine.getMedicineName());
            }

            double amount =
                    medicine.getMrp()
                    * dto.getQuantity();

            BillItem item =
                    new BillItem();

            item.setBill(savedBill);
            item.setMedicine(medicine);
            item.setQuantity(
                    dto.getQuantity());

            item.setUnitPrice(
                    medicine.getMrp());

            item.setAmount(amount);
            item.setMedicineName(medicine.getMedicineName());
            item.setBatchNo(medicine.getBatchNo());
            item.setScheduleType(medicine.getScheduleType());
            item.setUnit(medicine.getUnit());
            item.setMrp(medicine.getMrp());
            item.setExpiryDate(medicine.getExpiryDate());

            billItemRepository.save(item);

            medicine.setQuantity(
                    medicine.getQuantity()
                    - dto.getQuantity());

            medicineRepository.save(medicine);

            subTotal += amount;
        }

        // GST 18%
        double gst = subTotal * 0.18;

        // Discount
        double discount =
                request.getDiscount() == null
                ? 0
                : request.getDiscount();

        double total =
                subTotal - discount;

        double paid =
                request.getPaidAmount() == null
                ? 0
                : request.getPaidAmount();

        double balance =
                total - paid;

        savedBill.setSubTotal(subTotal);
        savedBill.setGst(gst);
        savedBill.setDiscount(discount);
        savedBill.setTotalAmount(total);

        savedBill.setPaidAmount(paid);
        savedBill.setBalanceAmount(balance);
        double oldBalance = customer.getBalance() == null ? 0 : customer.getBalance();

        double netAmount = bill.getTotalAmount();

        double paidAmount = bill.getPaidAmount();

        double remainingAmount = netAmount - paidAmount;

        customer.setBalance(
            oldBalance + remainingAmount
        );

        customerRepository.save(customer);
        return billRepository.save(savedBill);
    }

    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }
   
    public List<Bill> searchBills(String customer, String type) {
        return billRepository.searchBills(customer, type);
    }
}