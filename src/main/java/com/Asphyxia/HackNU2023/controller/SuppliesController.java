package com.Asphyxia.HackNU2023.controller;

import com.Asphyxia.HackNU2023.dto.CreateResponse;
import com.Asphyxia.HackNU2023.dto.SaleDto;
import com.Asphyxia.HackNU2023.dto.SupplyDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supplies")
public class SuppliesController {
    @GetMapping
    private List<SupplyDto> getSuppliesList(@RequestParam(value = "barcode", required = true) String barcode,
                                         @RequestParam(value = "fromTime", required = true) String fromTime,
                                         @RequestParam(value = "toTime", required = true) String toTime) {
        return null;
    }

    @GetMapping("/{id}")
    private SupplyDto getSupplyById(@PathVariable("id") String id) {
        return null;
    }

    @PostMapping
    private CreateResponse createSupply(@RequestBody SupplyDto supplyDto) {
        return null;
    }

    @PostMapping("/{id}")
    private void editSupply(@PathVariable("id") String id,
                          @RequestBody SupplyDto supplyDto) {

    }

    @DeleteMapping("/{id}")
    private void deleteSupply(@PathVariable("id") String id) {

    }
}
