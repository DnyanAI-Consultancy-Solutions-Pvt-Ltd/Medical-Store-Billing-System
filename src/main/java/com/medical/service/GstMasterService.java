package com.medical.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medical.entity.GstMaster;
import com.medical.entity.Medicine;
import com.medical.repository.GstRepository;
import com.medical.repository.MedicineRepository;

@Service
public class GstMasterService {
@Autowired
private MedicineRepository medicineRepository;
@Autowired
    private GstRepository repository;

public GstMaster save(GstMaster request) {

    Medicine medicine =
            medicineRepository.findById(
                    request.getMedicine().getId())
            .orElseThrow();

    medicine.setGstPercent(request.getGstPercentage());

    medicineRepository.save(medicine);

    GstMaster gst = new GstMaster();

    gst.setMedicine(medicine);

    gst.setGstPercentage(request.getGstPercentage());

    return repository.save(gst);

}
    public List<GstMaster> getAll() {

        return repository.findAll();
    }

    public GstMaster get(Long id) {

        return repository.findById(id)
                .orElse(null);
    }

    public void delete(Long id) {

        repository.deleteById(id);
    }
    public Double getMedicineGST(String medicineName){

        return repository
                .findByMedicine_MedicineName(medicineName)
                .map(GstMaster::getGstPercentage)
                .orElse(0.0);

    }
    
}