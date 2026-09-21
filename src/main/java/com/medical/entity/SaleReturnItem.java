package com.medical.entity;

import jakarta.persistence.*;

@Entity
@Table(name="sale_return_items")
public class SaleReturnItem {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name="sale_return_id")
    private SaleReturn saleReturn;



    @ManyToOne
    @JoinColumn(name="medicine_id")
    private Medicine medicine;



    private Integer quantity;


    private Double amount;



    // getters setters


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public SaleReturn getSaleReturn() {
        return saleReturn;
    }


    public void setSaleReturn(SaleReturn saleReturn) {
        this.saleReturn = saleReturn;
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

}