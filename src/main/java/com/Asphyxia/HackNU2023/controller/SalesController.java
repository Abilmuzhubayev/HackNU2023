package com.Asphyxia.HackNU2023.controller;

import com.Asphyxia.HackNU2023.dto.CreateResponse;
import com.Asphyxia.HackNU2023.dto.SaleDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sales")
public class SalesController {

    @GetMapping
    private List<SaleDto> getSalesList(@RequestParam(value = "barcode", required = true) String barcode,
                                       @RequestParam(value = "fromTime", required = true) String fromTime,
                                       @RequestParam(value = "toTime", required = true) String toTime) {
        return null;
    }

    @GetMapping("/{id}")
    private SaleDto getSaleById(@PathVariable("id") String id) {
        return null;
    }

    @PostMapping
    private CreateResponse createSale(@RequestBody SaleDto saleDto) {
        return null;
    }

    @PostMapping("/{id}")
    private void editSale(@PathVariable("id") String id,
                          @RequestBody SaleDto saleDto) {

    }

    @DeleteMapping("/{id}")
    private void deleteSale(@PathVariable("id") String id) {

    }
}
