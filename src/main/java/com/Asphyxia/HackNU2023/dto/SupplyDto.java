package com.Asphyxia.HackNU2023.dto;

import lombok.Data;

@Data
public class SupplyDto {
    private Long id;
    private String barcode;
    private Long price;
    private Long quantity;
    private String supplyTime;
}
