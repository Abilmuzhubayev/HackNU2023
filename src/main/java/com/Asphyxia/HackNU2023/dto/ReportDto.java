package com.Asphyxia.HackNU2023.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReportDto {
    private String barcode;
    private Long quantity;
    private Long revenue;
    private Long netProfit;
}
