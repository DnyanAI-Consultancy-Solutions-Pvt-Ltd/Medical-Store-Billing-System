package com.medical.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medical.dto.ScheduleRequest;
import com.medical.entity.Medicine;
import com.medical.entity.ScheduleMaster;
import com.medical.repository.MedicineRepository;
import com.medical.repository.ScheduleRepository;

@Service
public class ScheduleMasterService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    public ScheduleMaster save(ScheduleRequest request){

        Medicine medicine;

        if(request.getMedicineId() != null){

            medicine = medicineRepository
                    .findById(request.getMedicineId())
                    .orElseThrow();

        }else{

            medicine = medicineRepository
                    .findByMedicineName(request.getMedicineName())
                    .orElse(null);

            if(medicine == null){

                medicine = new Medicine();

                medicine.setMedicineName(request.getMedicineName());

            }
        }

        // Update schedule in Medicine table
        medicine.setScheduleType(request.getScheduleType());

        medicine = medicineRepository.save(medicine);

        // Save Schedule Master
        ScheduleMaster schedule = new ScheduleMaster();

        schedule.setMedicine(medicine);

        schedule.setScheduleType(request.getScheduleType());

        return scheduleRepository.save(schedule);
    }

    public List<ScheduleMaster> getAll(){

        return scheduleRepository.findAll();

    }

}
