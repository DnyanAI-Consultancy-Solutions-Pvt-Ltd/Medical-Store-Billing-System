package com.medical.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.medical.dto.ScheduleRequest;
import com.medical.entity.ScheduleMaster;
import com.medical.repository.ScheduleRepository;
import com.medical.service.ScheduleMasterService;

@RestController
@RequestMapping("/api/schedule-master")
@CrossOrigin("*")
public class ScheduleMasterController {
	 @Autowired
	private ScheduleRepository scheduleMasterRepository;
    @Autowired
    private ScheduleMasterService service;

    @PostMapping
    public ScheduleMaster save(
            @RequestBody ScheduleRequest request){

        return service.save(request);

    }
    @GetMapping
    public List<ScheduleMaster> getAll(){

        return service.getAll();
    }

    @GetMapping("/medicine/{medicineId}")
    public ScheduleMaster getByMedicineId(
            @PathVariable Long medicineId){

        return scheduleMasterRepository
                .findByMedicineId(medicineId);

    }
    @GetMapping("/{id}")
    public ScheduleMaster getById(@PathVariable Long id){
        return scheduleMasterRepository.findById(id).orElseThrow();
    }

    @PutMapping("/{id}")
    public ScheduleMaster update(@PathVariable Long id,
                                 @RequestBody ScheduleMaster schedule){

        ScheduleMaster existing =
        		scheduleMasterRepository.findById(id).orElseThrow();

        existing.setMedicine(schedule.getMedicine());
        existing.setScheduleType(schedule.getScheduleType());

        return scheduleMasterRepository.save(existing);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
    	scheduleMasterRepository.deleteById(id);
    }
}