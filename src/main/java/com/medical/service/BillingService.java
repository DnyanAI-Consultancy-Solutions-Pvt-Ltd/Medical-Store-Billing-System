package com.medical.service;

import java.time.LocalDateTime;

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
    

    public BillingService(CustomerRepository customerRepository, MedicineRepository medicineRepository,
			BillRepository billRepository, BillItemRepository billItemRepository) {
		super();
		this.customerRepository = customerRepository;
		this.medicineRepository = medicineRepository;
		this.billRepository = billRepository;
		this.billItemRepository = billItemRepository;
	}


	public Bill generateBill(BillRequest request) {

        Customer customer =
                customerRepository.findById(request.getCustomerId())
                        .orElseThrow();

        double subTotal = 0;

        Bill bill = new Bill();
        bill.setCustomer(customer);
        bill.setBillDate(LocalDateTime.now());

        Bill savedBill = billRepository.save(bill);

        for (BillMedicineDto dto : request.getMedicines()) {

            Medicine medicine =
                    medicineRepository.findById(dto.getMedicineId())
                            .orElseThrow();

            if (medicine.getQuantity() < dto.getQuantity()) {
                throw new RuntimeException(
                        "Stock not available for "
                                + medicine.getCompanyName());
            }

            double amount =
                    medicine.getPrice() * dto.getQuantity();

            BillItem item = new BillItem();

            item.setBill(savedBill);
            item.setMedicine(medicine);
            item.setQuantity(dto.getQuantity());
            item.setUnitPrice(medicine.getPrice());
            item.setAmount(amount);

            billItemRepository.save(item);

            medicine.setQuantity(
                    medicine.getQuantity() - dto.getQuantity());

            medicineRepository.save(medicine);

            subTotal += amount;
        }

        double gst = subTotal * 0.18;
        double total = subTotal + gst;

        savedBill.setSubTotal(subTotal);
        savedBill.setGst(gst);
        savedBill.setTotalAmount(total);

        return billRepository.save(savedBill);
    }
}