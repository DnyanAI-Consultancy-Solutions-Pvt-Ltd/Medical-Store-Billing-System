package com.medical.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.medical.entity.GstMaster;

public interface GstMasterRepository
extends JpaRepository<GstMaster, Long> {

}