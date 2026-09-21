package com.medical.entity;

import jakarta.persistence.*;

@Entity
@Table(name="suppliers")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String supplierName;
    private String drugLicenseNo;

    private String mobileNo;
    private String address;
    private String gstNo;
    @Column(name="balance")
    private Double balance;
    public String getDrugLicenseNo() {
		return drugLicenseNo;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public void setDrugLicenseNo(String drugLicenseNo) {
		this.drugLicenseNo = drugLicenseNo;
	}

	public Long getId() {
        return id;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGstNo() {
        return gstNo;
    }

    public void setGstNo(String gstNo) {
        this.gstNo = gstNo;
    }

	public void setId(Long id) {
		this.id = id;
	}
}