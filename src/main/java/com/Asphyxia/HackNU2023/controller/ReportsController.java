package com.Asphyxia.HackNU2023.controller;

import com.Asphyxia.HackNU2023.dto.ReportDto;
import com.Asphyxia.HackNU2023.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/reports")
public class ReportsController {

    @Autowired
    private ReportService reportService;


    @GetMapping
    private ReportDto getReport(@RequestParam(name = "barcode") BigInteger barcode,
                                @RequestParam(name = "fromTime") String fromTime,
                                @RequestParam(name = "toTime") String toTime) {

        ReportDto result = null;
        result = reportService.generateFastReport(barcode, fromTime, toTime);
//        try {
//            result = reportService.generateReport(barcode, fromTime, toTime);
//        } catch (ParseException e) {
//            log.error("ParseException in generateReport: ", e);
//        }

        return result;
    }

 }
