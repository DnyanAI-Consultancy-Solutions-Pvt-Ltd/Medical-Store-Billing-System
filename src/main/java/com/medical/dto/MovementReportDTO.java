
package com.medical.dto;

public class MovementReportDTO {

    private String medicineName;

    private Integer openingStock;

    private Integer purchaseQty;

    private Integer saleQty;

    private Integer purchaseReturnQty;

    private Integer saleReturnQty;

    private Integer closingStock;

	public String getMedicineName() {
		return medicineName;
	}

	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}

	public Integer getOpeningStock() {
		return openingStock;
	}

	public void setOpeningStock(Integer openingStock) {
		this.openingStock = openingStock;
	}

	public Integer getPurchaseQty() {
		return purchaseQty;
	}

	public void setPurchaseQty(Integer purchaseQty) {
		this.purchaseQty = purchaseQty;
	}

	public Integer getSaleQty() {
		return saleQty;
	}

	public void setSaleQty(Integer saleQty) {
		this.saleQty = saleQty;
	}

	public Integer getPurchaseReturnQty() {
		return purchaseReturnQty;
	}

	public void setPurchaseReturnQty(Integer purchaseReturnQty) {
		this.purchaseReturnQty = purchaseReturnQty;
	}

	public Integer getSaleReturnQty() {
		return saleReturnQty;
	}

	public void setSaleReturnQty(Integer saleReturnQty) {
		this.saleReturnQty = saleReturnQty;
	}

	public Integer getClosingStock() {
		return closingStock;
	}

	public void setClosingStock(Integer closingStock) {
		this.closingStock = closingStock;
	}

}