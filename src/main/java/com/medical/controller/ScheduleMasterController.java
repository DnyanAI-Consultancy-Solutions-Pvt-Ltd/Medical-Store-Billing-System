package com.medical.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.medical.entity.ScheduleMaster;
import com.medical.service.ScheduleMasterService;

@RestController
@RequestMapping("/api/schedule-master")
@CrossOrigin("*")
public class ScheduleMasterController {

    @Autowired
    private ScheduleMasterService service;

    @PostMapping
    public ScheduleMaster save(
            @RequestBody ScheduleMaster schedule){

        return service.save(schedule);
    }

    @GetMapping
    public List<ScheduleMaster> getAll(){

        return service.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Long id){

        service.delete(id);
    }
}