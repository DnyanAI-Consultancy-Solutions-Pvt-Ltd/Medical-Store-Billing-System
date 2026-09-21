package com.medical.dto;

import java.time.LocalDate;

public class PurchaseItemRequest {

	

	    private Long medicineId;
	    private Integer quantity;
	    private Double purchaseRate;
	    private Double saleRate;

	    private String batchNo;
	    private Double gst;
	    private String expiryDate;

	    // getters setters
	
    public Double getGst() {
	return gst;
}

public void setGst(Double gst) {
	this.gst = gst;
}


	
	public String getBatchNo() {
	return batchNo;
}

public void setBatchNo(String batchNo) {
	this.batchNo = batchNo;
}

public String getExpiryDate() {
	return expiryDate;
}

public void setExpiryDate(String expiryDate) {
	this.expiryDate = expiryDate;
}

	public Long getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(Long medicineId) {
        this.medicineId = medicineId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPurchaseRate() {
        return purchaseRate;
    }

    public void setPurchaseRate(Double purchaseRate) {
        this.purchaseRate = purchaseRate;
    }

    public Double getSaleRate() {
        return saleRate;
    }

    public void setSaleRate(Double saleRate) {
        this.saleRate = saleRate;
    }
}