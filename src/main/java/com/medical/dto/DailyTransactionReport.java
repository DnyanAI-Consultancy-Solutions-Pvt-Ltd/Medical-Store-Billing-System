package com.medical.dto;



public class DailyTransactionReport {

    private Double totalSales;
    private Double totalPurchases;
    private Double totalPayments;
    private Double saleReturns;
    private Double purchaseReturns;
    private Double profit;

    public Double getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(Double totalSales) {
        this.totalSales = totalSales;
    }

    public Double getTotalPurchases() {
        return totalPurchases;
    }

    public void setTotalPurchases(Double totalPurchases) {
        this.totalPurchases = totalPurchases;
    }

    public Double getTotalPayments() {
        return totalPayments;
    }

    public void setTotalPayments(Double totalPayments) {
        this.totalPayments = totalPayments;
    }

    public Double getSaleReturns() {
        return saleReturns;
    }

    public void setSaleReturns(Double saleReturns) {
        this.saleReturns = saleReturns;
    }

    public Double getPurchaseReturns() {
        return purchaseReturns;
    }

    public void setPurchaseReturns(Double purchaseReturns) {
        this.purchaseReturns = purchaseReturns;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }
}