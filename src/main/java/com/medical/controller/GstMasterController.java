package com.medical.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.medical.entity.GstMaster;
import com.medical.service.GstMasterService;

@RestController
@RequestMapping("/api/gst-master")
@CrossOrigin("*")
public class GstMasterController {

    @Autowired
    private GstMasterService service;

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
    public GstMaster get(
            @PathVariable Long id) {

        return service.get(id);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Long id) {

        service.delete(id);
    }
}