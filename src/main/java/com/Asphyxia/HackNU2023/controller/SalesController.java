package com.Asphyxia.HackNU2023.controller;

import com.Asphyxia.HackNU2023.dto.CreateResponse;
import com.Asphyxia.HackNU2023.dto.SaleDto;
import com.Asphyxia.HackNU2023.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/sales")
public class SalesController {

    @Autowired
    private SaleService saleService;


    @GetMapping
    private List<SaleDto> getSalesList(@RequestParam(value = "barcode", required = true) BigInteger barcode,
                                       @RequestParam(value = "fromTime", required = true) String fromTime,
                                       @RequestParam(value = "toTime", required = true) String toTime) {
        return saleService.getSalesByPeriod(barcode, fromTime, toTime);
    }

    @GetMapping("/{id}")
    private SaleDto getSaleById(@PathVariable("id") Long id) {
        return saleService.getSaleById(id);
    }

    @PostMapping
    private CreateResponse createSale(@RequestBody SaleDto saleDto) {
        CreateResponse createResponse = new CreateResponse();
        createResponse.setId(saleService.addSale(saleDto));
        return createResponse;
    }

    @PostMapping("/{id}")
    private void editSale(@PathVariable("id") Long id,
                          @RequestBody SaleDto saleDto) {
        saleDto.setId(id);
        saleService.editSale(saleDto);
    }

    @DeleteMapping("/{id}")
    private void deleteSale(@PathVariable("id") Long id) {
        saleService.deleteSale(id);
    }


}
