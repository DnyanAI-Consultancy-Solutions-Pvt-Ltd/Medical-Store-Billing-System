package com.medical.dto;

import java.util.List;

public class BillRequest {

    private Long customerId;

    private List<BillMedicineDto> medicines;

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