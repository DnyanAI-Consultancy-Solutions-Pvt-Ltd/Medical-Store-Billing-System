package com.medical.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.medical.entity.ScheduleMaster;

public interface ScheduleMasterRepository
extends JpaRepository<ScheduleMaster, Long>{

}