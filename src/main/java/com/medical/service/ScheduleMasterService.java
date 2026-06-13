package com.medical.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medical.entity.ScheduleMaster;
import com.medical.repository.ScheduleMasterRepository;

@Service
public class ScheduleMasterService {

    @Autowired
    private ScheduleMasterRepository repository;

    public ScheduleMaster save(
            ScheduleMaster schedule){

        return repository.save(schedule);
    }

    public List<ScheduleMaster> getAll(){

        return repository.findAll();
    }

    public void delete(Long id){

        repository.deleteById(id);
    }
}