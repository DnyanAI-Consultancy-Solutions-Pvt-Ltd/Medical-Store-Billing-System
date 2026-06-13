package com.medical.service;

import com.medical.entity.Medicine;
import com.medical.repository.MedicineRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineService {

    private final MedicineRepository medicineRepository;

    public MedicineService(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    public Medicine addMedicine(Medicine medicine) {
        return medicineRepository.save(medicine);
    }

    public List<Medicine> getAllMedicines() {
        return medicineRepository.findAll();
    }

    public Medicine getMedicineById(Long id) {
        return medicineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicine not found"));
    }

    public Medicine updateMedicine(Long id, Medicine medicine) {

        Medicine existing = getMedicineById(id);

        existing.setMedicineName(medicine.getMedicineName());
        existing.setCompanyName(medicine.getCompanyName());
        existing.setMrp(medicine.getMrp());
        existing.setQuantity(medicine.getQuantity());
        existing.setExpiryDate(medicine.getExpiryDate());

        return medicineRepository.save(existing);
    }

    public void deleteMedicine(Long id) {
        medicineRepository.deleteById(id);
    }
}