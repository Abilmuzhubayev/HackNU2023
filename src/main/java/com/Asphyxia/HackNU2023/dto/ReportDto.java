package com.Asphyxia.HackNU2023.dto;

import lombok.Data;

@Data
public class ReportDto {
    private String barcode;
    private Long quantity;
    private Long revenue;
    private Long netProfit;
}
