package com.Asphyxia.HackNU2023.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigInteger;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "sale", schema = "umag_hacknu", indexes = @Index(name = "sale_indx", columnList = "barcode, sale_time"))
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigInteger barcode;

    private Long price;

    private Long quantity;

    @Column(name = "sale_time")
    private Timestamp time;
}
