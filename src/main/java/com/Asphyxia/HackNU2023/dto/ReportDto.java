package com.Asphyxia.HackNU2023.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;

@Data
@AllArgsConstructor
public class ReportDto {
    private BigInteger barcode;
    private Long quantity;
    private Long revenue;
    private Long netProfit;
}
