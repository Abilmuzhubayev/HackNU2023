package com.Asphyxia.HackNU2023.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigInteger;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "barcode_cursor", indexes = @Index(name = "bc_index", columnList = "barcode, supplyTime"))
public class BarcodeCursor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigInteger barcode;

    private Timestamp supplyTime;

    private Long quantityUsed;

}
