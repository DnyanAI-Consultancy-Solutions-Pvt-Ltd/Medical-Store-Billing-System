package com.medical.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="sale_returns")
public class SaleReturn {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private Long billId;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Medicine medicine;

    private Integer quantity;

    private Double amount;

    private LocalDate returnDate;
    @OneToMany(mappedBy = "saleReturn",
            cascade = CascadeType.ALL)
 private List<SaleReturnItem> items = new ArrayList<>();
	public Long getId() {
		return id;
	}

	public List<SaleReturnItem> getItems() {
		return items;
	}

	public void setItems(List<SaleReturnItem> items) {
		this.items = items;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBillId() {
		return billId;
	}

	public void setBillId(Long billId) {
		this.billId = billId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Medicine getMedicine() {
		return medicine;
	}

	public void setMedicine(Medicine medicine) {
		this.medicine = medicine;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public LocalDate getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}

}
