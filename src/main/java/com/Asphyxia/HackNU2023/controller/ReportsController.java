package com.Asphyxia.HackNU2023.controller;

import com.Asphyxia.HackNU2023.dto.ReportDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportsController {
    private ReportDto getReport(@RequestParam(name = "barcode") String barcode,
                                @RequestParam(name = "fromTime") String fromTime,
                                @RequestParam(name = "toTime") String toTime) {
        return null;
    }
 }
