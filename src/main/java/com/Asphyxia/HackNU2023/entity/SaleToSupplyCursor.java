package com.Asphyxia.HackNU2023.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigInteger;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "sale_to_supply_cursor")
public class SaleToSupplyCursor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigInteger barcode;

    private Timestamp supplyTime;

    private Long quantityUsed;

    private Timestamp saleTime;

}
