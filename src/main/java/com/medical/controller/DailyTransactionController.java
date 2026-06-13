package com.medical.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medical.dto.DailyTransactionReport;
import com.medical.service.DailyTransactionService;

@RestController
public class DailyTransactionController {

    @Autowired
    private DailyTransactionService service;

    @GetMapping("/api/daily-report")
    public DailyTransactionReport getReport() {

        return service.getTodayReport();
    }
}