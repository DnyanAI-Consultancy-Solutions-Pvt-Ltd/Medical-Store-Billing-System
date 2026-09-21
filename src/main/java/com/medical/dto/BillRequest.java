package com.medical.dto;

import java.util.List;

public class BillRequest {

	private Long customerId;
	private String customerName;
    public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	private String doctorName;

    private String billType;

    private Double discount;

    private Double paidAmount;

    private List<BillMedicineDto> medicines;
	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public List<BillMedicineDto> getMedicines() {
		return medicines;
	}

	public void setMedicines(List<BillMedicineDto> medicines) {
		this.medicines = medicines;
	}
    
}