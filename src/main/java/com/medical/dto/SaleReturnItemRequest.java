package com.medical.dto;

public class SaleReturnItemRequest {


    private Long medicineId;

    private Integer quantity;

    private Double amount;



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


    public Double getAmount() {
        return amount;
    }


    public void setAmount(Double amount) {
        this.amount = amount;
    }

}