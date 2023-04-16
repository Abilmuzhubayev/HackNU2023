package com.Asphyxia.HackNU2023.dto;

import lombok.Data;

import java.math.BigInteger;

@Data
public class SupplyDto {
    private Long id;
    private BigInteger barcode;
    private Long price;
    private Long quantity;
    private String supplyTime;
}
