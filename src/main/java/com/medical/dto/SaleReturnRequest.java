package com.medical.dto;

import java.time.LocalDate;
import java.util.List;

public class SaleReturnRequest {

    private Long billId;

    private LocalDate returnDate;

    private List<SaleReturnItemRequest> items;


    public Long getBillId() {
        return billId;
    }


    public void setBillId(Long billId) {
        this.billId = billId;
    }


    public LocalDate getReturnDate() {
        return returnDate;
    }


    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }


    public List<SaleReturnItemRequest> getItems() {
        return items;
    }


    public void setItems(List<SaleReturnItemRequest> items) {
        this.items = items;
    }

}