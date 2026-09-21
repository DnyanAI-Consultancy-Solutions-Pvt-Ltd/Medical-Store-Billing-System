package com.medical.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.medical.entity.GstMaster;
import com.medical.repository.GstRepository;
import com.medical.service.GstMasterService;

@RestController
@RequestMapping("/api/gst-master")
@CrossOrigin("*")
public class GstController {
    @Autowired
private GstRepository repository;
    @Autowired
    private GstMasterService service;
    @GetMapping("/gst/{medicineName}")
    public Double getGST(
    @PathVariable String medicineName){

        return service
                .getMedicineGST(medicineName);

    }
    @PostMapping
    public GstMaster save(
            @RequestBody GstMaster gst) {

        return service.save(gst);
    }

    @GetMapping
    public List<GstMaster> getAll() {

        return service.getAll();
    }

    

   
    @GetMapping("/{id}")
    public GstMaster getById(@PathVariable Long id){
        return repository.findById(id).orElseThrow();
    }

    @PutMapping("/{id}")
    public GstMaster updateGST(@PathVariable Long id,
                               @RequestBody GstMaster gst){

        GstMaster existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("GST Not Found"));

        existing.setMedicine(gst.getMedicine());
        existing.setGstPercentage(gst.getGstPercentage());

        return repository.save(existing);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        repository.deleteById(id);
    }
}