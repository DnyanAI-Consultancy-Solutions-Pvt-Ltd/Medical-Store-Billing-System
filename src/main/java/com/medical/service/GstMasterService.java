package com.medical.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medical.entity.GstMaster;
import com.medical.repository.GstMasterRepository;

@Service
public class GstMasterService {

    @Autowired
    private GstMasterRepository repository;

    public GstMaster save(GstMaster gst) {

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
}