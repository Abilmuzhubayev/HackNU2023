package com.Asphyxia.HackNU2023.controller;

import com.Asphyxia.HackNU2023.dto.CreateResponse;

import com.Asphyxia.HackNU2023.dto.SupplyDto;
import com.Asphyxia.HackNU2023.service.ReportService;
import com.Asphyxia.HackNU2023.service.SupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/supplies")
public class SuppliesController {

    @Autowired
    private SupplyService supplyService;
    @GetMapping
    private List<SupplyDto> getSuppliesList(@RequestParam(value = "barcode", required = true) BigInteger barcode,
                                         @RequestParam(value = "fromTime", required = true) String fromTime,
                                         @RequestParam(value = "toTime", required = true) String toTime) {
        return supplyService.getSuppliesByPeriod(barcode, fromTime, toTime);
    }

    @GetMapping("/{id}")
    private SupplyDto getSupplyById(@PathVariable("id") Long id) {
        return supplyService.getSupplyById(id);
    }

    @PostMapping
    private CreateResponse createSupply(@RequestBody SupplyDto supplyDto) {
        CreateResponse createResponse = new CreateResponse();
        createResponse.setId(supplyService.addSupply(supplyDto));
        return createResponse;
    }

    @PostMapping("/{id}")
    private void editSupply(@PathVariable("id") Long id,
                          @RequestBody SupplyDto supplyDto) {
        supplyDto.setId(id);
        supplyService.editSupply(supplyDto);
    }

    @DeleteMapping("/{id}")
    private void deleteSupply(@PathVariable("id") Long id) {
        supplyService.deleteSupply(id);
    }

}
